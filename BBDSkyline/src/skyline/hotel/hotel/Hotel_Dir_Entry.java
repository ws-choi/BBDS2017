package skyline.hotel.hotel;

import index.rtree.dimitris.RTDataNode;
import index.rtree.dimitris.RTDirNode;
import index.rtree.dimitris.RTNode;
import index.rtree.query.skyline.HeapEntry;

import skyline.hotel.global.SKY_Constants;

import data.Point_Float;

public class Hotel_Dir_Entry extends HeapEntry {

	

	public RTNode node;
	public boolean isDataNode;
	
	public Hotel_Dir_Entry( RTNode node , Point_Float point ) {
		
/*		if( !(node instanceof RTNode) )
			MyConstants.error("not node.", true);
	*/	
		this.node = node;
		
		if( node instanceof RTDataNode )
		{
			isDataNode = true;
			RTDataNode thisnode = (RTDataNode) node;
			
			float[] cut = new float[4];
			for (int i = 0; i < cut.length; i++) {
				cut[i] = thisnode.get_mbr()[i];
			}
			dist = SKY_Constants.MINDIST(point.toArray(), cut );
			dist = (float) Math.sqrt(dist);
			dist += thisnode.get_mbr()[4];
			
			mbr = thisnode.get_mbr().clone();
		}
		
		else if ( node instanceof RTDirNode)
		{
			isDataNode = false;
			RTDirNode thisnode = (RTDirNode) node;
			
			float[] cut = new float[4];
			for (int i = 0; i < cut.length; i++) {
				cut[i] = thisnode.get_mbr()[i];
			}
			dist = SKY_Constants.MINDIST(point.toArray(), cut );
			dist = (float) Math.sqrt(dist);
			dist += thisnode.get_mbr()[4];

			mbr = thisnode.get_mbr().clone();
		}
		
		else
			SKY_Constants.error("not node.", true);
	}
	
}
