package data.generator2;

import index.rtree.dimitris.Data;


public class AntiCorrGenWithC extends DataGenerator2{

	float c;
	
	public AntiCorrGenWithC(int dim, float[][] bounds, boolean isFloatEnable, double d)
			throws Exception {
		super(dim, bounds, isFloatEnable);
		
		if(d<0) throw new Exception();
		if(d>1) throw new Exception();
		
		this.c=(float)d;
		
	}

	@Override
	Data getNext() {
		
		Data result;

		do{
			result = get_sample();
		}while(!satisfying(result, c));

		
		for (int i = 0; i < dim; i++) {
			result.data[2*i] = result.data[2*i+1] = (bounds[i][1]-bounds[i][0]) * result.data[2*i] + bounds[i][0]  ;
		}
		
		return result;
		
		
	}

	private boolean satisfying(Data result, float c) {
		
		float sum = 0;
		for (int i = 0; i < dim; i++) 
			sum += result.data[2*i];
		
		if(sum<dim-1)	return false;
		if(sum>dim-1+c) return false;
		return true;
	}

	Data get_sample(){
		Data result = new Data(dim);
		for (int i = 0; i < dim; i++) 
		{
			result.data[2*i] = result.data[2*i+1] = (float) (Math.random());
		}
		return result;
	}
/*
	
	public static void main(String[] args) throws Exception {
	
		float[][] bounds = {
				{0, 10000},
				//{0, 10000},
				{0, 10000}

		};
		AntiCorrGenWithC agc = new AntiCorrGenWithC(2, bounds, false, 1);
		
		for (int i = 0; i < 10000; i++) {
			System.out.print(agc.getNext_String());
				
		}
		
	}*/
	
}
