package grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import processing.core.PApplet;
/*
 * Object to store all different types of grid
 * points
 */
public class GridCollection{
	private ArrayList<ArrayList<Grid>> ALLGRID;
	private PApplet canvas;
	
	public GridCollection(PApplet canvas) {
		ALLGRID = new ArrayList<ArrayList<Grid>>();
	}
	
	public void addGrid(ArrayList<Grid> newGrid) {
		ALLGRID.add(newGrid);
	}
	
	public void display(int index) {
		//TODO: add custom exception
		for (ArrayList<Grid> custom: ALLGRID) {
			custom.get(index).display();
		}
	}
	
	/*
	 * Clear the Grid collection
	 */
	public void clear(){
		ALLGRID.clear();
	}

	
}
