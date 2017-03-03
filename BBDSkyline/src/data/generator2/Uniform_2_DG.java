package data.generator2;

import index.rtree.dimitris.Data;

public class Uniform_2_DG extends DataGenerator2{

	public Uniform_2_DG(int dim, float[][] bounds, boolean isFloatEnable)
			throws Exception {
		super(dim, bounds, isFloatEnable);
	}

	@Override
	Data getNext() {

		Data result = new Data(dim);
		
		for (int i = 0; i < dim; i++) 
			result.data[2*i] = result.data[2*i+1] = sample_RV(i);
		
		return result;
		
	}
	
	
	float sample_RV(int dim) {

		float min = bounds[dim][0];
		float max = bounds[dim][1];
		float rv01 = (float) (Math.random() * (max-min) + min) ;
		
		return auto_cast(rv01);
	}


}
