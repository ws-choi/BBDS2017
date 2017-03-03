package skyline.hotel.experiment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import data.Point_Float;

public class Dynamic_LESS_for_Hotel {
		
	File data_file;
	
	public Dynamic_LESS_for_Hotel(File data_file) {
		this.data_file = data_file;
	}
		
    private float distance(Hotel_object o, Point_Float point) 
    {
    	float distance = (float) Math.sqrt((o.x-point.x)*(o.x-point.x) + (o.y-point.y)*(o.y-point.y));
    	
    	return distance;
    }


	String nextline (BufferedReader br) throws IOException{
		return  br.readLine();
	}
	
	Hotel_object get_hotel (String input, Point_Float point)
	{
		String[] buf = input.split(",");
		Hotel_object obj = new Hotel_object();
		obj.oid = (int) Float.parseFloat(buf[0]);
		obj.x = Float.parseFloat(buf[1]);
		obj.y = Float.parseFloat(buf[2]);
		obj.dis = distance(obj.x,obj.y, point);
		obj.price = Float.parseFloat(buf[3]); 
		
		return obj;
	}

	
    private float distance(float x, float y, Point_Float point) {
    	
    	float distance = (float) Math.sqrt((x-point.x)*(x-point.x) + (y-point.y)*(y-point.y));
    	return distance;
	}

	public LinkedList<Hotel_object> return_skyline_obj (Point_Float point) throws IOException 
    {
    	// �߰� ��ī�̶��� ��ü�� ������� ��ũ�帮��Ʈ skyline_object_list
    	LinkedList<Hotel_object> candidates = new LinkedList<Hotel_object>();

    	BufferedReader outer_loop_interator = new BufferedReader(new FileReader(data_file), 256);

    	// ��ǲ�� ù �����͸� skyline_object_list�� ����
    	candidates.add( get_hotel(outer_loop_interator.readLine(), point));
				
    	String outer_list;
    	//��� ��ǲ �����͸� ��ĵ�� �� ���� �˰��� ����
    	while ( (outer_list = nextline(outer_loop_interator)) != null )
    	{
        	//���� ��ü�� �ҷ���
    		Hotel_object outer = get_hotel(outer_list, point);
        	
    		int skyline_list_length = candidates.size();
 
    		boolean isSkyline = true;
    		LinkedList<Hotel_object> list = new LinkedList<Hotel_object>();
    		
    		for (int i=0; i < skyline_list_length; i++)
    		{
    			//skyline_object_list���� ��ü�� �ҷ���
    			Hotel_object inner = (Hotel_object) candidates.get(i);
    			
    			
    			//����

    			if(outer.price > inner.price && outer.dis > inner.dis) {
    			
    				isSkyline = false;
    				break;       
    			}
    			
    			else if(outer.price < inner.price && outer.dis < inner.dis) 
    				list.add(inner);
    			
    			else continue;
    				    			
    		}
    		
    		Iterator<Hotel_object> iter = list.iterator();
    		
    		while(iter.hasNext()) candidates.remove(iter.next());
    	
    		if(isSkyline) candidates.add(outer);
    		
    		sort(candidates);
      	}

    	outer_loop_interator.close();
  
    	return candidates;

    }

	private void sort(LinkedList<Hotel_object> candidates) {
		Collections.sort(candidates, new Comparator<Hotel_object>() {

			@Override
			public int compare(Hotel_object o1, Hotel_object o2) {
				
				if(o1.price + o1.dis > o2.price + o2.dis)
					return 1;
				
				else if (o1.price + o1.dis < o2.price + o2.dis)
					return -1;
				
				return 0;
			}
		});
		
	}
	
	public static void main(String[] args) {
		
		LinkedList<Hotel_object> x = new LinkedList<Hotel_object>();
		
		Hotel_object a = new Hotel_object();
		Hotel_object b = new Hotel_object();
		Hotel_object c = new Hotel_object();
		Hotel_object d = new Hotel_object();
		
		a.dis=0; a.price=0;
		b.dis=1; b.price=1;
		c.dis=2; c.price=2;
		d.dis=3; d.price=3;
		
		x.add(b);
		x.add(a);
		x.add(d);
		x.add(c);
		
		Dynamic_LESS_for_Hotel sorter = new Dynamic_LESS_for_Hotel(null);
		
		sorter.sort(x);

		for (int i = 0; i < 4; i++) {
			System.out.println(x.removeFirst().price);
		}
	}

}
