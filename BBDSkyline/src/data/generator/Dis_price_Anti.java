package data.generator;

import data.Point_Float;
import index.rtree.dimitris.Data;

public class Dis_price_Anti extends UniformGen {

	Point_Float point;
	private float ratio;
	int neg_dim = 2 ;
	
	float[][] ndg_range = {{-10000, 0}};
	
	NormDistGen ndg = new NormDistGen(1, ndg_range, true);
	
	public Dis_price_Anti(Point_Float point, float[][] bounds, boolean isFloatEnable)
			throws Exception {
		super(3, bounds, isFloatEnable);
		
		this.point = point;
		
		float max_x_diff = (point.x < (bounds[0][0] + bounds[0][1] )/2) ? 
				bounds[0][1] : bounds[0][0];

		float max_y_diff = (point.y < (bounds[1][0] + bounds[1][1] )/2) ? 
				bounds[1][1] : bounds[1][0];
		
		float xy_max = (max_x_diff - point.x)*(max_x_diff - point.x) +
				(max_y_diff - point.y)*(max_y_diff - point.y);
		
		xy_max = (float) Math.sqrt(xy_max);
		
		ratio = bounds[neg_dim ][1] - bounds[neg_dim][0];
		ratio /= xy_max;
		ratio *= -1;
	}
	
	@Override
	public Data getNext() {
		
		Data buf =	super.getNext();
		
		//x 0 1
		//y 2 3
		//price 4 5
		
		buf.data[4]=4;
		
		float dist = (buf.data[0] - point.x) * (buf.data[0] - point.x) 
				+ (buf.data[2] - point.y) *(buf.data[2] - point.y) ;
						
		dist = (float) Math.sqrt(dist);
		
		buf.data[2*neg_dim] = buf.data[2*neg_dim+1] = ratio * dist + bounds[neg_dim][1] + 10*ndg.getNext().data[0];
		
		return buf;
		
	}
	
	@Override
	public String getNext_String() {
	
		Data buf = this.getNext();
		
		String result = new String();
		
		for (int i = 0; i < dim; i++) 
		{ 
			if(i+1 == dim)
				result += buf.data[2*i] + "\n";
			else
				result += buf.data[2*i] + ", ";
					
		}
		
		return result;
		
	}

}
