package index.rtree.query.skyline;

import index.rtree.dimitris.RTDataNode;
import index.rtree.dimitris.RTDirNode;
import index.rtree.dimitris.RTNode;

import skyline.hotel.global.SKY_Constants;


public class Dir_Entry extends HeapEntry
{

	public RTNode node;
	public boolean isDataNode;
	
	public Dir_Entry( RTNode node ) {

		if( !(node instanceof RTNode) )
			SKY_Constants.error("not node.", true);

		this.node = node;

		if( node instanceof RTDataNode )
		{
			isDataNode = true;
			RTDataNode thisnode = (RTDataNode) node;

			dist = 0;

			for(int i = 0 ; i< thisnode.get_dim(); i++)
				dist += thisnode.get_mbr()[i*2] ;

			mbr = thisnode.get_mbr().clone();
		}

		else if ( node instanceof RTDirNode)
		{
			isDataNode = false;
			RTDirNode thisnode = (RTDirNode) node;

			dist = 0;

			for(int i = 0 ; i< thisnode.get_dim(); i++)
				dist += thisnode.get_mbr()[i*2] ;

			mbr = thisnode.get_mbr().clone();
		}

		else
			SKY_Constants.error("not node.", true);
	}

	public Dir_Entry(RTNode node, float[] weight) {

		if( !(node instanceof RTNode) )
			SKY_Constants.error("not node.", true);

		this.node = node;

		if( node instanceof RTDataNode )
		{
			isDataNode = true;
			RTDataNode thisnode = (RTDataNode) node;

			dist = 0;

			for(int i = 0 ; i< thisnode.get_dim(); i++)
				dist += thisnode.get_mbr()[i*2] * weight[i];

			mbr = thisnode.get_mbr().clone();
		}

		else if ( node instanceof RTDirNode)
		{
			isDataNode = false;
			RTDirNode thisnode = (RTDirNode) node;

			dist = 0;

			for(int i = 0 ; i< thisnode.get_dim(); i++)
				dist += thisnode.get_mbr()[i*2] * weight[i];

			mbr = thisnode.get_mbr().clone();
		}

		else
			SKY_Constants.error("not node.", true);


	}
}


