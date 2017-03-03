package skyline.hotel.hotel;

import gui.event.Skyline_Query_Event;
import index.basic_ds.SortedLinList;
import index.rtree.dimitris.Data;
import index.rtree.dimitris.DirEntry;
import index.rtree.dimitris.RTDataNode;
import index.rtree.dimitris.RTDirNode;
import index.rtree.dimitris.RTNode;
import index.rtree.dimitris.RTree;
import index.rtree.query.skyline.BBSkylineGUI;
import index.rtree.query.skyline.HeapEntry;

import javax.swing.JPanel;





import skyline.hotel.global.SKY_Constants;

import data.Point_Float;


public class Hotel_Sky extends BBSkylineGUI {
	
	public Hotel_Sky(RTree tree, JPanel target, Skyline_Query_Event query, long time) {

		super(tree, target, query, time);
	}
	
	public Hotel_Sky(RTree tree, Point_Float point) {
		
		super(tree, point);
	}
	
	

	public SortedLinList h_skyline() {

		tree.load_root();
		
		RTNode root_ptr = tree.root_ptr;
	
		SortedLinList Result_Set = new SortedLinList();
		SortedLinList heap = new SortedLinList();
		
		if(tree.root_is_data)
		{
		}
		
		else{
			
			RTDirNode node = (RTDirNode) root_ptr;
			
			DirEntry[] entries = node.entries;
			
			for (int i = 0; i < node.get_num(); i++) {
				DirEntry entry = entries[i];
				heap.insert(new Hotel_Dir_Entry(entry.get_son(), point));
			}
		}
				
		heap.sort();
		
		while(heap.get_num() > 0)
		{
			HeapEntry obj = (HeapEntry) heap.get_first(); heap.erase();
						
			if( SKY_Constants.isDominated_hotel(point,obj.mbr, Result_Set)) continue;
			
			else{
				
				if(obj instanceof Hotel_Entry){
					Hotel_Entry hotel_entry = (Hotel_Entry) obj;					
					hotel_entry.data.distanz = hotel_entry.dist;
					Result_Set.insert(obj);
					
					Hotel hotel = (Hotel) (hotel_entry.data);
					hotel.selected = true;
					
					sleep_for_a_while(sleep_time);
					
					if(target != null)
					target.repaint();
				}
				
				else{
					
					tree.page_access ++;
					
					Hotel_Dir_Entry entry = (Hotel_Dir_Entry) obj;
					if(entry.isDataNode)
					{
						RTDataNode node = (RTDataNode)entry.node;
						
						Data[] data = node.data;
						for (int i = 0; i < node.get_num(); i++) {
							Data child = data[i];
							
							if(SKY_Constants.isDominated_hotel(point,child.data, Result_Set) ) continue;
							
							else heap.insert(new Hotel_Entry(child, point) );
						}
							
					}
					
					else{
						
						RTDirNode node = (RTDirNode)entry.node;
						
						DirEntry[] entries = node.entries;
						for (int i = 0; i < node.get_num(); i++) {
							
							DirEntry child = entries[i];
							
							if(child.son_is_data)
							{
								RTDataNode child_ptr = (RTDataNode) child.get_son();
								
								if( SKY_Constants.isDominated_hotel(point,child_ptr.get_mbr(), Result_Set) ) continue;
								
								else
									heap.insert(new Hotel_Dir_Entry(child_ptr, point));
							}
							
							else{
								
								RTDirNode child_ptr = (RTDirNode) child.get_son();
								
								if( SKY_Constants.isDominated_hotel(point,child_ptr.get_mbr(), Result_Set) ) continue;
								
								else
									heap.insert(new Hotel_Dir_Entry(child_ptr, point));
								
							}
						}
						
					}
					
				heap.sort();	
				}
			}

		}
		
		Result_Set.sort();

		return Result_Set;
	}

	
	@Override
	public void run() {
		tree.page_access=0;
		h_skyline();
	}

	
	
}
