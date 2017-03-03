package skyline.hotel.experiment;

import java.security.InvalidParameterException;

public class Naive_Hotel {

	public int id;
	public float[] data;
	
	public Naive_Hotel(int id, float[] input) {
		
		this.id = id;
		
		if(input.length != 3) throw new InvalidParameterException();
		this.data = input.clone();
	}
	
	public Naive_Hotel() {
		this.data = new float[3];
	}
}
