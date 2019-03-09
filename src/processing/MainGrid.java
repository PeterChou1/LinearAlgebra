package processing;
import java.util.ArrayList;

import matrix.Matrix;
import matrix.Vector;
import processing.core.PApplet;
import processing.core.PFont;
import ui.MatrixUI;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import grid.Grid;
import grid.Grid0D;
import grid.Grid1D;
import grid.Grid2D;
import grid.GridCollection;

public class MainGrid extends PApplet{
	//a controlP5 object
	// x0 and y0 define 
	// where the grid is centered
	private float x0;
	private float y0;
	// GUI controller for controlP5 library
	ControlP5 P5OBJ;
	// Stores all graphical grid objects
	Grid2D basisOrg;
	GridCollection ALLGRID;
	// integer to store slider value
	int matrixSlider = 0;
	// input for matrix
	MatrixUI matrixInput;

	public static void main(String[] args) {
		PApplet.main("processing.MainGrid");
	}

	public void settings() {
		// define window sizes
		size(800, 800);
		//controlP5.addButton("button1",1,70,10,60,20);
		// define center of grid
		x0 = (width/2);
		y0 = (height/2);
	}

	public void setup() {
		ALLGRID = new GridCollection(this);
		// set grid points
		Double[] magnitudeX = {10.0, 0.0};
		Double[] magnitudeY = {0.0, 10.0};
		Vector basisX = new Vector(magnitudeX);
		Vector basisY = new Vector(magnitudeY);
		basisOrg = new Grid2D(basisX, basisY, this);
		// Setup Matrix values
		
	
		
		// get new ControlP5 object
		P5OBJ = new ControlP5(this);
		// add a new slider
		//P5OBJ.addSlider("MatrixSlider")
		//	 .setRange(0, 100)
		//	 .setSize(700, 50)
		//	 .setLabelVisible(false);
		
		// add input object
		matrixInput = new MatrixUI(P5OBJ, 3, 2, 40, 200);
		
		// add button to visualize input object after its trigger
		P5OBJ.addButton("VISUALIZE")
		     .setPosition(100.0f, 100.0f);
		     
		
		
		// configuration setting
		// stroke weight
		strokeWeight(2);
		
		
	}
	

	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.getController().getName() == "MatrixSlider") {
			matrixSlider = Math.round(theEvent.getController().getValue());
		} else if (theEvent.getController().getName() == "VISUALIZE") {
			P5OBJ.remove("MatrixSlider");
			P5OBJ.addSlider("MatrixSlider")
			     .setRange(0, 100)
			     .setSize(700, 50)
			     .setLabelVisible(false)
			     .setPosition(20.0f, 20.0f);

			try {
				// get normal matrix from augmented matrix
				Matrix mat = new Matrix(matrixInput.getValue());
				
				ArrayList<Grid> gridarray =	basisOrg.getTranslate(mat, 101);
				ALLGRID.clear();
				ALLGRID.addGrid(gridarray);
			} catch(NumberFormatException e) {
				println(e.toString());
			}
		}
	}

	

	/*
	public void MatrixSlider(Double theColor) {
		  int myColor = color(theColor);
		  println("a slider event. setting background to "+theColor);
	}
	*/

	public void draw() {
		// refresh background with white
		background(255);
		pushMatrix();
		translate(x0, y0);
		ALLGRID.display(matrixSlider);
		line(-400, 0, 400, 0);
		line(0, -400, 0, 400);
		popMatrix();
	}
	/*
	 * Enable drag
	 */
	public void mouseDragged() {
		// if mouse is hovering over ui dont translate
		if (!P5OBJ.isMouseOver()) {
			x0 = x0 + (mouseX - pmouseX);
			y0 = y0 + (mouseY - pmouseY);
		}
	}

}
