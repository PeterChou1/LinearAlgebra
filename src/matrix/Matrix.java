package matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

import language.ExpressionNode;
import language.Parser;
import matrix.Vector;

public class Matrix {
	private int col;
	private int row;
	private Vector[] rowVec;
	private Vector[] colVec;
	private Double[][] AllVal;
	
	public Matrix(Double[][] Value){

		row = Value.length;
		col = Value[0].length;
		AllVal = Value;
		// initialized row vector array
		rowVec = new Vector[row];
		// initialize col vector array
		colVec = new Vector[col];
		// get row vectors
		for (int x = 0; x < row; x++) {
			rowVec[x] = new Vector(AllVal[x]);
		}
		// get column vector
		for (int x = 0; x < col; x++) {
			Double[] ColMag = new Double[row];
			for (int y = 0; y < row; y++) {
				ColMag[y] = AllVal[y][x];
			}
			colVec[x] = new Vector(ColMag);
		}
	}
	/*
	 * Extract Matrix Value from an augmented matrix
	 */
	public Matrix extract(){
		
		Double[][] newVal = new Double[col][row - 1];
		
		for(Double[] row: AllVal){
			
			
		}
		
		
	}
	/*
	 * Return Number of Column in Matrix
	 */

	public int col() {
		return col;
	}
	
	/*
	 * Return Number of Row in Matrix
	 */
	public int row() {
		return row;
	}
	
	
	public Matrix mult(Matrix MatrixB) {
		//Create new Matrix to populate
		Double[][] newValue = new Double[row][MatrixB.col()];
		Vector[] MatBcol = MatrixB.getCol();
		

		for (int x = 0; x < row; x++) {
			for (int y = 0; y < MatrixB.col(); y++) {
				Vector rowVecI = rowVec[x];
				Vector colVecI = MatBcol[y];
				newValue[x][y] = rowVecI.DotP(colVecI);
			}
		}
		return new Matrix(newValue);
	}
	
	public Vector mult(Vector VectB) {
		Double[] Values = new Double[row];
			
		for (int x = 0; x < row; x++) {
			Vector rowVecI = rowVec[x];
			Values[x] = rowVecI.DotP(VectB);
		}
		return new Vector(Values);

	}
	
	public Matrix scale(Double scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector[] getRow() {
		return rowVec.clone();
	}
	

	public Vector[] getCol() {
		return colVec.clone();
	}
	
	public Double get(int row, int col) {
		return AllVal[row][col];
	}
	

	public String toString() {
		String output = "";
		for (Double[] row: AllVal) {
			output += "[";
			for(Double element: row) {
				output += " " + element.toString() + " ";
			}
			output += "]" + System.lineSeparator();
		}
		return output;
	}
	public String[] solve() {
		Matrix REF = gaussianEliminate();
		return Matrix.BackSub(REF);
		
	}
	
	
	public ArrayList<Vector> getBasis(){
		Matrix REF = gaussianEliminate();
		String[] solution = Matrix.BackSub(REF);
		EqnVector Solution = new EqnVector(solution);
		return Solution.getBasis();
		
	}
	
	/*
	 * Reduce a matrix to its row echelon form
	 * then use back substitution to solve
	 * [1, 2, 3, 1]    [1, 2, 3, 1]
	 * [1, 2, 3, 1] -> [0, 0, 0, 0]
	 * [1, 2, 3, 1]    [0, 0, 0, 0]
	 * m x n means (m rows) (n cols)
	 * return matrix in Row Echelon form
	 */
	private Matrix gaussianEliminate(){
		//Start from the 0, 0 (top left)
		int rowPoint = 0;
		int colPoint = 0;
		// instantiate the array storing values for new Matrix
		Double[][] newVal = deepCopy(AllVal);
		while (rowPoint < row && colPoint < col) {
			// find the index with max number (absolute value) from rowPoint to row
			Double max = Math.abs(newVal[rowPoint][colPoint]);
			int index = rowPoint;
			int maxindex = rowPoint;
			while (index < row) {
				if (max < Math.abs(newVal[index][colPoint])) {
					max = newVal[index][colPoint];
					maxindex = index;
				}
				index++;
			}
			// if max is 0 then that must mean there are no pivots
			if (max == 0) {
				colPoint++;
			}else {
				//TODO: refactor into method
				Double[] Temp = newVal[rowPoint];
				newVal[rowPoint] = newVal[maxindex];
				newVal[maxindex] = Temp;
				// Fill 0 lower part of pivot
				for(int lower = rowPoint + 1; lower < row; lower++) {
					Double ratio = newVal[lower][colPoint]/newVal[rowPoint][colPoint];
					newVal[lower][colPoint] = (Double) 0.0;
					// adjust for the remaining element
					for (int remain = colPoint + 1; remain < col; remain++) {
						newVal[lower][remain] = newVal[lower][remain] - newVal[lower][remain]*ratio;
						
					}
				}
				rowPoint++;
				colPoint++;	
			}
		}
		return new Matrix(newVal);
	}
	
	
	/*
	 * Given a matrix in REF use back substitution to
	 * solve
	 * REQ: at least 2 columns
	 * REQ: matrix in REF
	 */
	private static String[] BackSub(Matrix REF){
		// Start at bottom right of Matrix -1 to offset starting from index 0
		// Track two index
		// [1, 2, 3| 1] 
		// [1, 2, 3| 1] 
		// [1, 2, 3| 1] < (rowPoint, augmentY)
		//		  ^
		//(rowPoint, colPoint)
		//	  REF.col
		// ------------
		// [          ] |
		// [          ] | REF.row
		// [          ] |
		int rowPoint = REF.row() - 1;
		int colPoint = REF.col() - 2;
		int augmentY = REF.col() - 1;
		Double[][] REFval = REF.getAll();
		// if matrix inconsistent the last rows will look like [0,...,0| 1]
		if (REFval[rowPoint][colPoint] == 0 && REFval[rowPoint][augmentY] != 0) {
			return null;
		}else {
			String[] Solution = new String[REF.col - 1];
			// find first pivot
			// we now have form ax + b + .. + c = d
			// x = (d - b - .. - c)/a
			while (rowPoint >= 0) {
				Double[] row = REFval[rowPoint];
				Matrix.processRow(Solution, row);
				rowPoint--;
			}
			return Solution;
		}
	}
	
	private static void processRow(String[] prev, Double[] mag) {
		// - 1 because last index does not count
		int index = 0;
		Double variable = mag[index];
		String Solution = String.valueOf(mag[mag.length - 1]);
		// find first non zero pivot
		while (variable == 0.0f && index < mag.length - 1) {
			index++;
			variable = mag[index];
		}
		// if variable is 0 then entire row is 0 which carries no
		// information so we ignore it
		// otherwise we process it
		if (variable != 0.0f) {
			for (int free = index + 1; free < mag.length - 1; free++) {
				if (prev[free] == null) {
					prev[free] = "x" + free; 
				}
				// do not subtract from answer if multiple is 0
				if (mag[free] != 0.0) {
					Solution += "-" + mag[free] + "*" + prev[free];
				}
			}
			Solution = "(" + Solution + ")/" + variable;
			prev[index] = Solution;
		}
		
	}
	

	public Double[][] getAll(){
		return deepCopy(AllVal);
	}

	/*
	 * Deep Copy for 2d Double matrix
	 */
	public static Double[][] deepCopy(Double[][] allVal2) {
	    if (allVal2 == null) {
	        return null;
	    }

	    final Double[][] result = new Double[allVal2.length][];
	    for (int i = 0; i < allVal2.length; i++) {
	        result[i] = Arrays.copyOf(allVal2[i], allVal2[i].length);
	        // For Java versions prior to Java 6 use the next:
	        // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
	    }
	    return result;
	}
	
	public static void main(String[] args) {
		Double[][] myValues = new Double[2][3];
		myValues[0][0] = 1.0; 
		myValues[0][1] = 2.0;
		myValues[0][2] = 3.0;
		myValues[1][0] = 0.0;
	    myValues[1][1] = 0.0;
	    myValues[1][2] = 0.0;
	
	
		Matrix myMatrix = new Matrix(myValues);
		Matrix myMat = myMatrix.gaussianEliminate();
		String[] mySol = Matrix.BackSub(myMat);
		System.out.println("Original Matrix");
		System.out.println(myMatrix.toString());
		System.out.println("REF matrix");
		System.out.println(myMat.toString());
		
		// parse input
		System.out.println("SOLUTION");
		Parser myParse = new Parser();
		ArrayList<ExpressionNode> solParse = new ArrayList<ExpressionNode>();
		for (String sol: mySol){
			solParse.add(myParse.parse(sol));
			System.out.println(sol);
		}
		
		ArrayList<Vector> myVector = myMatrix.getBasis();
		

	}

}
