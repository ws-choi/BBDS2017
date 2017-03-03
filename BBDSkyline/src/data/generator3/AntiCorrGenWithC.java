package data.generator3;

import index.rtree.dimitris.Data;


public class AntiCorrGenWithC extends DataGenerator3 {

	float c;
	
	public AntiCorrGenWithC(int dim, double _c) throws Exception {

		super(dim);
		
		if(_c<0) throw new Exception();
		if(_c>1) throw new Exception();
		
		this.c=(float)_c;
		
	}

	@Override
	Data getNext() {
		
		Data result;

		do{
			result = get_sample();
		}while(!satisfying(result, c));

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

	
}
