package grid;

import java.util.ArrayList;
import matrix.Matrix;
import matrix.Vector;
import processing.core.PApplet;

public class Grid1D implements Grid{
	// store vector to calculate translation logic
	Vector Basis;
	Vector offset;
	PApplet canvas;
	// store points for display for faster display
	Double x1;
	Double y1;
	Double x2;
	Double y2;

	private Grid1D(Vector Basis, Vector offset,  PApplet canvas) {
		this.canvas = canvas;
		this.Basis = Basis;
		this.offset = offset;
		// Basis
		Vector reverse = Basis.scale(-1.0);
		this.x1 = Basis.values()[0] + offset.values()[0];
		this.y1 = Basis.values()[1] + offset.values()[1];
		this.x2 = reverse.values()[0] + offset.values()[0];
		this.y2 = reverse.values()[1] + offset.values()[1];
	}
	
	/*
	 * Create a new Grid1D object by vector basis
	 * this will automatically scale up basis
	 * to cover the screen
	 */
	public static Grid1D constructWithScale(Vector Basis, Vector offset, PApplet canvas) {
		//TODO implement 0.0 magnitude case
		Double scalingFactor = canvas.width / Basis.mag();
		Basis = Basis.scale(scalingFactor);
		Grid1D newGrid = new Grid1D(Basis, offset, canvas);
		return newGrid;
	}

	/*
	 *  Create a new Grid1D object by vector basis
	 *  this will NOT automatically scale up basis
	 *  to cover the screen
	 */
	public static Grid1D constructNoScale(Vector Basis, Vector offset, PApplet canvas) {
		Grid1D newGrid = new Grid1D(Basis, offset, canvas);
		return newGrid;
	}

	@Override
	public void display() {
		canvas.line( x1.floatValue(), y1.floatValue(), 
				     x2.floatValue(), y2.floatValue());
		
	}
	

	@Override
	public ArrayList<Grid> getTranslate(Matrix translation, int length) {
		ArrayList<Grid> translateArray = new ArrayList<Grid>();
		// target point will be the last item in the array
		Vector target = translation.mult(Basis);
		// reverse direction and add to our target to get
		// direction needed
		Vector reversed = Basis.scale(-1.0);
		
		Vector direction = target.add(reversed)
								 .scale((double) 1/(length - 1));
				
		// add all the grid point needed
		Vector transVector = Basis;
		for (int index = 0; index < length; index++) {
			Grid1D transGrid = Grid1D.constructNoScale(transVector, offset, canvas);
			translateArray.add(transGrid);
			transVector = transVector.add(direction);
		}
		return translateArray;
	}

}
