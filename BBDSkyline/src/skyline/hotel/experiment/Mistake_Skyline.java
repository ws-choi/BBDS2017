package skyline.hotel.experiment;

import java.util.LinkedList;


public class Mistake_Skyline {

	LinkedList<Nested_obj> obj_list;
	
	public Mistake_Skyline(LinkedList<Nested_obj> list) {
		obj_list = list;
	}
	public LinkedList<Nested_obj> skyline ( ) 
    {

       // new linked list for buffering skyline objects
       LinkedList<Nested_obj> skyline_object_list = new LinkedList<Nested_obj>();

       // iterator for two linked lists      
       java.util.Iterator<Nested_obj> outer_loop_iterator = obj_list.iterator();

       // add first item to list
       skyline_object_list.add((Nested_obj) obj_list.getFirst());
            
       while (outer_loop_iterator.hasNext())
       {
           Nested_obj outer_obj_buffer = outer_loop_iterator.next();
           
          //inner loop iteration times
          int skyline_list_length = skyline_object_list.size();
          //System.out.println("the size of skyline list" + skyline_object_list.size() + "  ");

          for (int i=0; i < skyline_list_length; i++)
          {
             Nested_obj inner_obj_buffer = (Nested_obj) skyline_object_list.get(i);
             
             /*********************************************************************/
              /*                      skyline algorithm                            */
              /*********************************************************************/             
             if (outer_obj_buffer.price < inner_obj_buffer.price &&
                   outer_obj_buffer.distance < inner_obj_buffer.distance &&
                   outer_obj_buffer.dominating == false )
             {
                skyline_object_list.add(outer_obj_buffer);
                skyline_object_list.remove(i);
                i--;
                outer_obj_buffer.dominating = true;
                continue;
             }
             
             else if (outer_obj_buffer.price < inner_obj_buffer.price &&
                   outer_obj_buffer.distance < inner_obj_buffer.distance &&
                   outer_obj_buffer.dominating == true)
             {
                skyline_object_list.remove(i); 
                i--;                 
                continue;
             }
             
             else if (outer_obj_buffer.price >= inner_obj_buffer.price &&
                      outer_obj_buffer.distance >= inner_obj_buffer.distance)
             {
                break;
             }
              
             else if(i+1 == skyline_list_length)
             {
                skyline_object_list.add(outer_obj_buffer);
                break;
             }
             else 
                continue;
                
          }
       
         }
       return skyline_object_list;
   	
    }
	
}


