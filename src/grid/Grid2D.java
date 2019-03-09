package grid;

import java.util.ArrayList;
import matrix.Matrix;
import matrix.Vector;
import processing.core.PApplet;

public class Grid2D implements Grid{
	Vector BasisX;
	Vector BasisY;
	PApplet canvas;
	ArrayList<Grid1D> gridlines;
	public Grid2D(Vector BasisX, Vector BasisY, PApplet canvas) {
		this.BasisX = BasisX;
		this.BasisY = BasisY;
		this.canvas = canvas;
		// 2D GRID 
		gridlines = new ArrayList<Grid1D>();
		// Create X grid lines grid1D
		
		Double offsetValue  = -20.0;
		Vector offsetX = BasisX.scale(-20.0);
		Vector offsetY = BasisY.scale(-20.0);
		while (offsetValue <= 20.0) {
			Grid1D lineX = Grid1D.constructWithScale(BasisX, offsetY, canvas);
			Grid1D lineY = Grid1D.constructWithScale(BasisY, offsetX, canvas);
			offsetX = BasisX.scale(offsetValue);
			offsetY = BasisY.scale(offsetValue);
			gridlines.add(lineX);
			gridlines.add(lineY);
			offsetValue += 1.0;
		}
	}

	@Override
	public void display() {
		for (Grid1D line: gridlines) {
			line.display();
		}
	}

	@Override
	public ArrayList<Grid> getTranslate(Matrix translation, int length) {
		ArrayList<Grid> translateArray = new ArrayList<Grid>();
		// target point will be the last item in the array
		Vector targetX = translation.mult(BasisX);
		Vector targetY = translation.mult(BasisY);
		// reverse direction and add to our target to get
		// direction needed
		Vector reversedX = BasisX.scale(-1.0);
		Vector directionX = targetX.add(reversedX)
								   .scale((double) 1/(length - 1));
		Vector reversedY = BasisY.scale(-1.0);
		Vector directionY = targetY.add(reversedY)
								   .scale((double) 1/(length - 1));
				
		// add all the grid point needed
		Vector transVectorX = BasisX;
		Vector transVectorY = BasisY;
		for (int index = 0; index < length; index++) {
			Grid2D transGrid = new Grid2D(transVectorX, transVectorY, canvas);
			translateArray.add(transGrid);
			transVectorX = transVectorX.add(directionX);
			transVectorY = transVectorY.add(directionY);
		}

		return translateArray;
	}


}
