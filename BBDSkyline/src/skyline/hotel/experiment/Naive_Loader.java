package skyline.hotel.experiment;

import index.rtree.dimitris.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Naive_Loader {

	File file;
	BufferedReader br;
	public Naive_Loader(File file) throws IOException {
		this.file= file;
				
	}
	
	public LinkedList<Data> getList() throws IOException{
		
		br = new BufferedReader(new FileReader(file));
		
		LinkedList<Data> res = new LinkedList<Data>();


		String line;
		
		br.readLine();
		
		while( (line = br.readLine()) != null )
		{
			String[] buf = line.split(",");
			Data tuple = new Data();
			tuple.id = Integer.parseInt(buf[0]);
			tuple.data[0] = tuple.data[1] = Float.parseFloat(buf[1]);
			tuple.data[2] = tuple.data[3] = Float.parseFloat(buf[2]);

			res.add(tuple);
		}
				
		br.close();
		
		return res;
	}
	
	public LinkedList<Naive_Hotel> getHotelList() throws IOException{
		
		br = new BufferedReader(new FileReader(file));
		
		LinkedList<Naive_Hotel> res = new LinkedList<Naive_Hotel>();


		String line;
		
		br.readLine();
		
		while( (line = br.readLine()) != null )
		{
			String[] buf = line.split(",");
			Naive_Hotel tuple = new Naive_Hotel();
			tuple.id = Integer.parseInt(buf[0]);
			tuple.data[0] = Float.parseFloat(buf[1]);
			tuple.data[1] = Float.parseFloat(buf[2]);
			tuple.data[2] = Float.parseFloat(buf[3]);

			res.add(tuple);
		}
				
		br.close();
		
		return res;
	}
	
	public LinkedList<Nested_obj> get_mistake_list() throws NumberFormatException, IOException {
		
		br = new BufferedReader(new FileReader(file));
		
		LinkedList<Nested_obj> obj_list = new LinkedList<Nested_obj>();
		
		Nested_obj obj;
		String line;
		
		br.readLine();
		
		while( (line = br.readLine()) != null )
	
		{
			String[] buf = line.split(",");
			obj = new Nested_obj();
			obj.oid = (int) Float.parseFloat(buf[0]);
			obj.price = Float.parseFloat(buf[1]);
			obj.distance = Float.parseFloat(buf[2]);
			//obj.dominating = false;
		   
			//insert object to linked list and increase counter
			obj_list.add(obj);
			
		}
		
		return obj_list;
	}

	public LinkedList<hyun_object> get_hkstake_list() throws IOException {

		br = new BufferedReader(new FileReader(file));
		
		LinkedList<hyun_object> obj_list = new LinkedList<hyun_object>();
		
		hyun_object obj;
		String line;
		
		br.readLine();
		
		while( (line = br.readLine()) != null )
		{
			String[] buf = line.split(",");
			obj = new hyun_object();
			obj.oid = (int) Float.parseFloat(buf[0]);
			obj.x = Float.parseFloat(buf[1]);
			obj.y = Float.parseFloat(buf[2]);
			obj.price = Float.parseFloat(buf[3]); 
			//obj.dominating = false;
		
			//insert object to linked list and increase counter
	        obj_list.add(obj);
	  
		}
		
		return obj_list;
	}
}
