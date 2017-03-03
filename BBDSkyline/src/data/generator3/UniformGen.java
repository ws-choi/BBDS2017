package data.generator3;

import index.rtree.dimitris.Data;

public class UniformGen extends DataGenerator3 {

	public UniformGen(int dim )	throws Exception {
		super(dim);
	}

	@Override
	Data getNext() {

		Data result = new Data(dim);
		
		for (int i = 0; i < dim; i++) 
			result.data[2*i] = result.data[2*i+1] = (float) Math.random() ;
		
		return result;
		
	}

}
