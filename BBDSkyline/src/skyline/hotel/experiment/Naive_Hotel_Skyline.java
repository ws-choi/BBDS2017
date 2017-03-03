package skyline.hotel.experiment;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import data.Point_Float;

public class Naive_Hotel_Skyline {


//	LinkedList<Naive_Hotel> list;
	File data_file;
	Point_Float point;
	
	public Naive_Hotel_Skyline(File data_file, Point_Float point) {


		this.data_file = data_file;
		this.point=new Point_Float(point.x, point.y);
		
	}

	String nextline (BufferedReader br) throws IOException{
		return  br.readLine();
	}
	
	Naive_Hotel get_hotel (String input)
	{
		String[] buf = input.split(",");
		Naive_Hotel tuple = new Naive_Hotel();
		tuple.id = Integer.parseInt(buf[0]);
		tuple.data[0] = Float.parseFloat(buf[1]);
		tuple.data[1] = Float.parseFloat(buf[2]);
		tuple.data[2] = Float.parseFloat(buf[3]);
		
		return tuple;
	}

	public LinkedList<Naive_Hotel> skyline() throws IOException {
		
		boolean amIDominated;
		LinkedList<Naive_Hotel> result = new LinkedList<Naive_Hotel>();
	
		BufferedReader mylist = new BufferedReader( new FileReader(data_file), 256);

		mylist.readLine();
		
		String my_line, your_line;
		
		while( (my_line = nextline(mylist)) != null)
		{
			
			Naive_Hotel me = get_hotel(my_line);
			amIDominated = false;

			BufferedReader yourlist = new BufferedReader( new FileReader(data_file), 256);
			yourlist.readLine();

			while ( (your_line = nextline(yourlist)) != null)
			{
				
				if(isDominated(me, get_hotel(your_line))){
					amIDominated = true;
					break;
				}
			}
			
			if(!amIDominated) result.add(me);
			
			yourlist.close();
		}
		
		mylist.close();
		
/*
		for (int i = 0; i < size; i++) {
			
			Naive_Hotel me = list.get(i);

			boolean imALooser = false;

			for (int j = 0; j < size; j++) {

				Naive_Hotel you = list.get(j);
			
				if(isDominated(me, you))
				{
					imALooser = true;
					break;
				}
				else continue;
			}
			if(!imALooser) result.add(me);
		}*/
		
		return result;
	}
	
	


	public boolean isDominated (Naive_Hotel me, Naive_Hotel you){

		
		float my_dist = ( point.x - me.data[0] ) * ( point.x - me.data[0] ) 
				+ ( point.y - me.data[1] )*( point.y - me.data[1] );
		
		float your_dist = ( point.x - you.data[0] ) * ( point.x - you.data[0] ) 
				+ ( point.y - you.data[1] )*( point.y - you.data[1] );

		float myprice = me.data[2];
		
		float yourprice = you.data[2];
		
		boolean same = true;
				
		if(my_dist < your_dist)
			return false;
		else if(myprice > yourprice)
			same = false;
					
		if(!same) return true;
		else return false;		
	}


	
}
