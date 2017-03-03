package skyline.hotel.hotel;

import index.rtree.dimitris.Data;
import index.rtree.query.skyline.HeapEntry;

import skyline.hotel.global.SKY_Constants;

import data.Point_Float;

public class Hotel_Entry extends HeapEntry{

	
	public Data data;
	
	public Hotel_Entry( Data data, Point_Float point ) {
		
		if( !(data instanceof Hotel) )
			SKY_Constants.error("not data.", true);
		
		this.data = data;
		
		dist = 
				(data.get_mbr()[0] - point.x) 
				*(data.get_mbr()[0] - point.x) 
				+ 
				
				(data.get_mbr()[2] - point.y) 
				*(data.get_mbr()[2] - point.y) ;
		
		dist = (float) Math.sqrt(dist);
		dist += data.get_mbr()[4];
		
		mbr = data.data.clone();
		
	}
	

	
}
