package ui;

import controlP5.ControlP5;
import controlP5.Textfield;
import exception.InvalidInputException;
import processing.core.PApplet;
import processing.core.PFont;
// creates a 
public class MatrixUI {
	ControlP5 P5OBJ;
	String[][] MatrixString;
	int width; 
	int height;
	int xpos;
	int ypos;
	
	public MatrixUI(ControlP5 P5OBJ, int x, int y, int xpos, int ypos) {
		this.width = x;
		this.height = y;
		this.xpos = xpos;
		this.ypos = ypos;
		this.P5OBJ = P5OBJ;
		MatrixString = new String[y][x];
		int xrange = 0;
		int yrange = 0;
		// set up a matrix of text inputs and store then in a list
		while(yrange < y) {
			int xstore = xpos;
			while (xrange < x) {
				String name = "input" + Integer.toString(xrange) + Integer.toString(yrange);
				MatrixString[yrange][xrange] = name;
				P5OBJ.addTextfield(name)
	     	 		 .setPosition(xpos, ypos)
	     	 		 .setSize(40,40)
	     	 		 .setFocus(true)
	     	 		 .setAutoClear(false)
	     	 		 .setLabelVisible(false)
	     	 		 .setColorLabel(0);
				// position horizontal between box
				xpos += 60;
				xrange += 1;
			}
			xrange = 0;
			xpos = xstore;
			// position vertical between box
			ypos += 60;
			yrange += 1;
		}
	}
	/*
	 * Get value from matrix fields
	 */
	public Double[][] getValue() throws NumberFormatException{
		
		Double[][] returnValue = new Double[height][width];
		for (int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				String value = P5OBJ.get(Textfield.class, MatrixString[y][x]).getText();
				// Throw NumberFormatException if value is not Double
				Double valueParse = Double.parseDouble(value);
				returnValue[y][x] = valueParse;
			}
		}
		return returnValue;
	}
}
