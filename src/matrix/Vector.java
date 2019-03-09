package matrix;

public class Vector{
	

	private Double[] values;
	private int Dim;
	
	public Vector(Double[] values) {
		// Store Double for getters
		this.values = values;
		Dim = values.length;
		// Store magnitude

	}
	/*
	 * Calculate magnitude
	 */
	public Double mag() {
		Double mag = 0.0;
		for (Double value: values) {
			mag += Math.pow(value, 2);
		}
		mag = Math.sqrt(mag);
		return mag;		
	}
	
	/*
	 * Normalize Vector
	 */
	public Vector normalize() {
		Double[] newMag = new Double[values.length];
		Double divisor = 1 / this.mag();
		
		for(int i = 0; i < values.length; i++ ) {
			newMag[i] = values[i] * divisor;
		}
		return new Vector(newMag);
	}

	public Double DotP(Vector product) {
	
		// Normal definition of dot product
		Double[] mag2 = product.values();
		Double dotp = 0.0;
		for(int i = 0; i <  mag2.length; i++) {
			dotp += values[i] * mag2[i];
		}	
		return dotp;
	}

	public Vector add(Vector addition) {

		Double[] mag2 = addition.values();
		Double[] newMag = new Double[this.values.length];
		// Normal definition of addition
		for(int i = 0; i < values.length; i++ ) {
			newMag[i] = values[i] + mag2[i];
		}
		return new Vector(newMag);		

	}
	
	public Vector scale(Double scalar) {
		
		Double[] newMag = new Double[values.length];
		for(int i = 0; i < values.length; i++) {
			newMag[i] = values[i] * scalar;
		}
		return new Vector(newMag);
	}

	/*
	 * Returns Dimension of the vector
	 */
	public int Dim() {
		return Dim;
	}
	
	/*
	 * Stores all coordinates of vector
	 * (Double)
	 */
	public Double[] values() {
		return values.clone();
	}
	
	
	@Override
	public String toString(){
		String returnstr = "[";
		
		for (Double value: values){
			returnstr += " " + Double.toString(value) + " ";
		}
		
		return returnstr + "]";
	}
}
