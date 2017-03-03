package index.rtree.query.skyline;

import index.rtree.dimitris.Data;

import skyline.hotel.global.SKY_Constants;

public class Data_Entry extends HeapEntry
{

	public Data data;
	
	public Data_Entry( Data data ) {
		
		if( !(data instanceof Data) )
			SKY_Constants.error("not data.", true);
		
		this.data = data;
		
		for(int i = 0 ; i< data.dimension; i++)
			dist += data.get_mbr()[i*2];	
		
		mbr = data.data.clone();
		
	}
	
}