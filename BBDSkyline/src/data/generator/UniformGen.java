package data.generator;



public class UniformGen extends DataGenerator{

	public UniformGen(int dim, float[][] bounds, boolean isFloatEnable) throws Exception {
		super(dim, bounds, isFloatEnable);
	}
	


	@Override
	float sample_RV(int dim) {

		float min = bounds[dim][0];
		float max = bounds[dim][1];
		float rv01 = (float) (Math.random() * (max-min) + min) ;
		
		return auto_cast(rv01);
	}

}
