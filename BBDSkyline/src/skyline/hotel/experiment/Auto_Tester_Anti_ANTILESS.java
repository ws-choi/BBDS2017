package skyline.hotel.experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import skyline.hotel.experiment2.Dynamic_ANTILESS_for_Hotel;
import skyline.hotel.experiment2.Hotel_object;

import data.Point_Float;

public class Auto_Tester_Anti_ANTILESS {

	public static void main(String[] args) throws IOException {
		
		BufferedReader query_reader = new BufferedReader(new FileReader(
				new File("E:/Dropbox/109. My code/1. LabClibse/Sky_Hotel_Final/query.csv")));
		
		File data_file = new  File("E:/Dropbox/109. My code/1. LabClibse/Sky_Hotel_Final/ANTI3_10000.csv");
		
		query_reader.readLine();
	
		
		
		//BNL: Index building

		//CSV HEADER
		System.out.println("Processing,BBSkylineGUI Cardinality");

		//BBS: Query Processing
				
		for (int i = 0; i < 1; i++) {
			Auto_Tester_Anti_ANTILESS.test_a_query (get_Next_query(query_reader), data_file);
		}
	}

	static private Point_Float get_Next_query(BufferedReader reader) throws IOException{
	
		String buf = reader.readLine();
		if(buf == null){
			reader.close();
			return null;
		}
		
		String[] parse = buf.split(",");
		
		Point_Float result = new Point_Float(Float.parseFloat(parse[0]),Float.parseFloat(parse[1]) );
		return result;
	}
	
	private static void test_a_query(Point_Float Next_query, File data) throws IOException {

		System.gc();

		long start1, end1;
		
		
		start1 = System.currentTimeMillis();
		LinkedList<Hotel_object> sout= (new Dynamic_ANTILESS_for_Hotel(data) ).return_skyline_obj(Next_query);
		end1 = System.currentTimeMillis();
		
		System.out.print(end1-start1 + ",");

		System.out.println(sout.size());
		
		sout = null;
		data = null;
	
		System.gc();

		
	}
	
}
