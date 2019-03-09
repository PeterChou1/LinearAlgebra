package matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import language.ExpressionNode;
import language.Parser;
import language.SetVariable;

/*
 * Vector Storing equations
 */
public class EqnVector{
	Parser parse;
	HashSet<String> freevar;
	ArrayList<ExpressionNode> parsedExp;
	
	public EqnVector(String[] equations){
		parse = new Parser();
		parsedExp = new ArrayList<ExpressionNode>();
		freevar = new HashSet<String>();
		for (String eqn: equations){
			ExpressionNode eqnParse = parse.parse(eqn);
			parsedExp.add(eqnParse);
			freevar.addAll(eqnParse.getVar());
		}
	}
	/*
	 * 
	 */
	public ArrayList<Vector> getBasis(){
		
		// number basis is equivalent to number
		// of free variable in the program
		Iterator<String> freeiter = freevar.iterator();
		ArrayList<Vector> basisArray = new ArrayList<Vector>();
		while (freeiter.hasNext()){
			String baseVar = freeiter.next();
			
			Double[] values1 = new Double[parsedExp.size()];
			Double[] values2 = new Double[parsedExp.size()];
			
			for (int expCount = 0; expCount < parsedExp.size(); expCount++){
				ExpressionNode eqn = parsedExp.get(expCount);
				HashSet<String> eqnFree = eqn.getVar();
				for (String var: eqnFree){
					// set all to 0 except for one free variable
					if (var != baseVar)
						eqn.accept(new SetVariable(var, 0.0));
				}
				eqn.accept(new SetVariable(baseVar, 1.0));
				values1[expCount] = eqn.getValue();
				eqn.accept(new SetVariable(baseVar, 2.0));
				values2[expCount] = eqn.getValue();
			}
			Vector point1 = new Vector(values1);
			Vector point2 = new Vector(values2);
			basisArray.add(point1.add(point2.scale(-1.0)));
		}
		
		return basisArray;
	}
	
	public Vector getOffSet(){
		Double[] values = new Double[parsedExp.size()];
		Iterator<ExpressionNode> parseIter = parsedExp.iterator();
		
		int counter = 0;
		while(parseIter.hasNext()){
			ExpressionNode eqn = parseIter.next();
			HashSet<String> eqnFree = eqn.getVar();
			for (String var: eqnFree){
				eqn.accept(new SetVariable(var, 0.0));
			}
			values[counter] = eqn.getValue();
			counter++;
		}
		
		return new Vector(values);
	}

}
