package data.generator;

public class RandomMeanNormDistGen extends NormDistGen {

	public RandomMeanNormDistGen(int dim, float[][] bounds, boolean isFloatEnable)
			throws Exception {		
		super(dim, bounds, isFloatEnable);
	}
	

	@Override
	protected void setmeanvar() throws Exception {

		means = new float[dim];
		vars = new float[dim];

		UniformGen udg= new UniformGen(dim, bounds, false);

		for (int i = 0; i < means.length; i++) {
			means[i] = udg.sample_RV(i);
			vars[i] =  50;
		}
	}
}
