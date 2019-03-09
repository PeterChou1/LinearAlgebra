package grid;

import java.util.ArrayList;
import matrix.Matrix;

public interface Grid {

	public void display();
	/*
	 * Returns an array of grid which translates to the 
	 * target grid
	 * length indicates how many grid will be in the 
	 * ArrayList
	 */
	public ArrayList<Grid> getTranslate(Matrix translation, int length);
	
}
