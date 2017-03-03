package data.generator2;

import index.rtree.dimitris.Data;

public class Correlated_Hotel_DG extends DataGenerator2{


	AntiCorrGenWithC anti_2_dim;
	
	public Correlated_Hotel_DG(float[][] bounds, boolean isFloatEnable, double c)
			throws Exception {
		super(3, bounds, isFloatEnable);
				
		float[][] bound0101 = { {0,1}, {0,1}};
		
		anti_2_dim = new AntiCorrGenWithC(2, bound0101, isFloatEnable, c);
	}
	
	

	@Override
	Data getNext() {
		
		Data dis_price = anti_2_dim.getNext();
		
		float r = dis_price.data[0];
		
		double theta = 2 * Math.PI * Math.random();
		
		float x = r * (float)Math.cos(theta);
		float y = r * (float)Math.sin(theta);
		
		Data result = new Data(3);
		
		result.data[0] = result.data[1] = (x + 1)/2 * (bounds[0][1] - bounds[0][0]);
		result.data[1] = result.data[2] = (y + 1)/2 * (bounds[1][1] - bounds[1][0]);
		result.data[3] = result.data[4] = dis_price.data[2] * (bounds[2][1] - bounds[2][0]);
		
		return result;
		
	}
	
	
	public static void main(String[] args) throws Exception {
	
		float[][] bounds = {
				{0, 10000},
				{0, 10000},
				{0, 10000}

		};

		Correlated_Hotel_DG agc = new Correlated_Hotel_DG(bounds, true, 0.1);
		for (int i = 0; i < 10000; i++) {
			System.out.print(agc.getNext_String());
				
		}
		
	}

}
