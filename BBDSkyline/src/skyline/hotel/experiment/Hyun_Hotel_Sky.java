package skyline.hotel.experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Hyun_Hotel_Sky {
	
//	LinkedList<hyun_object> obj_list;
	
	File data_file;
	
	public Hyun_Hotel_Sky(File data_file) {
		this.data_file = data_file;
	}
		
    static double distance(hyun_object o) 
    {
    	//if query point = (0,0)
    	double distance = Math.sqrt((o.x-0)*(o.x-0) + (o.y-0)*(o.y-0));
    	
    	return distance;
    }
    
    static void print_obj_list(LinkedList<hyun_object> obj_list) 
    {
    	//System.out.println();
    	//System.out.println("object id 	price		Distance");
    	
    	java.util.Iterator<hyun_object> iterator =obj_list.iterator();
    	
    	while (iterator.hasNext())
		{
			hyun_object obj_buffer = iterator.next();
			System.out.println(obj_buffer.oid +" 		"+ obj_buffer.price+" 		"+ distance(obj_buffer));
		}
    }

	String nextline (BufferedReader br) throws IOException{
		return  br.readLine();
	}
	
	hyun_object get_hotel (String input)
	{
		String[] buf = input.split(",");
		hyun_object obj = new hyun_object();
		obj.oid = (int) Float.parseFloat(buf[0]);
		obj.x = Float.parseFloat(buf[1]);
		obj.y = Float.parseFloat(buf[2]);
		obj.price = Float.parseFloat(buf[3]); 
		
		return obj;
	}

	
    public LinkedList<hyun_object> return_skyline_obj() throws IOException 
    {
    	// �߰� ��ī�̶��� ��ü�� ������� ��ũ�帮��Ʈ skyline_object_list
    	LinkedList<hyun_object> skyline_object_list = new LinkedList<hyun_object>();

    	// ��ǲ �����͸� ��ĵ�ϱ� ���� ���ͷ����� ���� 
    	//System.out.println(obj_list);

    	//java.util.Iterator<hyun_object> outer_loop_iterator = obj_list.iterator();

    	BufferedReader outer_loop_interator = new BufferedReader(new FileReader(data_file), 256);
    	outer_loop_interator.readLine();
    	
    	// ��ǲ�� ù �����͸� skyline_object_list�� ����
    	skyline_object_list.add( get_hotel(outer_loop_interator.readLine()));
				
    	String outer_list;
    	//��� ��ǲ �����͸� ��ĵ�� �� ���� �˰��� ����
    	while ( (outer_list = nextline(outer_loop_interator)) != null )
    	{
        	//���� ��ü�� �ҷ���
    		hyun_object outer_obj_buffer = get_hotel(outer_list);
        	
    		//skyline_object_list�� ����� �ҷ��ͼ� for���� ���� Ƚ���� ����
    		int skyline_list_length = skyline_object_list.size();

    		for (int i=0; i < skyline_list_length; i++)
    		{
    			//skyline_object_list���� ��ü�� �ҷ���
    			hyun_object inner_obj_buffer = (hyun_object) skyline_object_list.get(i);
    			
    			    			
    			//��ǲ �����Ϳ��� �ҷ��� ��ü�� ��ī�̶��� ��ü�� �е� �ϴ��� Ȯ��
    			if (outer_obj_buffer.price <= inner_obj_buffer.price ||
    					distance(outer_obj_buffer) <= distance(inner_obj_buffer))
    			{
    				//���ݰ� �Ÿ��� ������ ��� 
    				if (outer_obj_buffer.price == inner_obj_buffer.price && 					
    						distance(outer_obj_buffer) == distance(inner_obj_buffer))
    				{
    					continue;
    				}

    				//������ ���������� �Ÿ��� �� Ŭ��, Ȥ�� �Ÿ��� ���������� ������ �� ū ��� break
    				else if (outer_obj_buffer.price >= inner_obj_buffer.price &&
    						distance(outer_obj_buffer) >= distance(inner_obj_buffer))
        			{
       				
    					break;
        			}	
    				
    				//��ǲ �����Ϳ��� �ҷ��� ��ü�� ��ī�̶��� ��ü�� �е� �ϸ�
    				else if (outer_obj_buffer.price < inner_obj_buffer.price &&
    						distance(outer_obj_buffer) < distance(inner_obj_buffer))
    				{  
    					//�е����ϴ� ��ü skyline_object_list���� ����
    					skyline_object_list.remove(i);
    					i--;
    					skyline_list_length--;
    	    			
    					if(skyline_object_list.size()==0)
    	    			{
    	    				skyline_object_list.add(outer_obj_buffer);
    	    				break;
    	    			}
    					
    				}
    				
    				//�Ÿ��� ���������� ��ǲ ��ü�� ������ �� �������
    				else if (outer_obj_buffer.price < inner_obj_buffer.price &&
    						distance(outer_obj_buffer) == distance(inner_obj_buffer))
    				{
    					//�е����ϴ� ��ü skyline_object_list���� ����
    					skyline_object_list.remove(i);
    					i--;
    					skyline_list_length--;


    					if(skyline_object_list.size()==0)
    	    			{
    	    				skyline_object_list.add(outer_obj_buffer);
    	    				break;
    	    			}
    					
    						
    				}
    				
    				//������ ���������� ��ǲ ��ü�� �Ÿ��� �� ������� 
    				else if (outer_obj_buffer.price == inner_obj_buffer.price &&
    						distance(outer_obj_buffer) < distance(inner_obj_buffer))
    				{
    					//�е����ϴ� ��ü skyline_object_list���� ����
    					skyline_object_list.remove(i);
    					i--;
    					skyline_list_length--;


    					if(skyline_object_list.size()==0)
    	    			{
    	    				skyline_object_list.add(outer_obj_buffer);
    	    				break;
    	    			}
    					
    				}
    				
				//skyline_object_list�� �� ������� ��ǲ �����Ͱ� �е� ������ ������
    			if (i+1 == skyline_list_length)
    				{
    					//System.out.println("added item:"+ outer_obj_buffer.oid);
    					//skyline_object_list�� �߰�
    					
    					skyline_object_list.add(outer_obj_buffer);
    					break;
    				}
    			    			
    			}
    			
    			//��ǲ �����Ϳ��� �ҷ��� ��ü�� ��ī�̶��� ��ü�� ���Ͽ� �е� ���ϸ� break
    			else 
    				break;
    				
    		}
    	
      	}
    	//end of while loop
  
    	return skyline_object_list;
//    	print_obj_list(skyline_object_list);
//		//System.out.println("the size of skyline list" + skyline_object_list.size() + "  ");

    }
}
