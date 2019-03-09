package grid;

import java.util.ArrayList;
import matrix.Matrix;
import matrix.Vector;
import processing.core.PApplet;

public class Grid0D implements Grid{
	
	Vector point;
	Double x;
	Double y;
	PApplet canvas;
	
	public Grid0D(Vector point, PApplet canvas) {
		this.point = point;
		this.x = point.values()[0];
		this.y = point.values()[1];
		this.canvas = canvas;
		
	}

	@Override
	public void display() {
		canvas.line(0, 0, x.floatValue(), y.floatValue());
	}
	
	@Override
	public ArrayList<Grid> getTranslate(Matrix translation, int length) {
		ArrayList<Grid> translateArray = new ArrayList<Grid>();
		// target point will be the last item in the array
		Vector target = translation.mult(point);
		// reverse direction and add to our target to get
		// direction needed
		Vector reversed = point.scale(-1.0);
		Vector direction = target.add(reversed)
								 .scale((double) 1/(length - 1));
		
		// add all the grid point needed
		Vector transVector = point;
		
		for (int index = 0; index < length; index++) {
			Grid0D transGrid = new Grid0D(transVector, canvas);
			translateArray.add(transGrid);
			transVector = transVector.add(direction);
		}
		
		return translateArray;
	}

	@Override
	public String toString() {
		return String.valueOf(point.values()[0]) + ":" + String.valueOf(point.values()[1]);
		
	}

}
