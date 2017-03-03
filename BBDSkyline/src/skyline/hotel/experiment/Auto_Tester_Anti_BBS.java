package skyline.hotel.experiment;

import index.basic_ds.SortedLinList;
import index.rtree.dimitris.RTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import skyline.hotel.hotel.Hotel_Sky;

import data.Point_Float;

public class Auto_Tester_Anti_BBS {

	public static void main(String[] args) throws IOException {
		
		long start, end;

		BufferedReader query_reader = new BufferedReader(new FileReader(
				new File("E:/Dropbox/109. My code/1. LabClibse/Sky_Hotel_Final/query.csv")));
		
		File data_file = new  File("E:/Dropbox/109. My code/1. LabClibse/Sky_Hotel_Final/ANTI3_10000.csv");
		
		query_reader.readLine();
	
		
		//BBS: Index building
		start = System.currentTimeMillis();
		Hotel_Bulk_Loader ibl = new Hotel_Bulk_Loader(data_file);					
		end = System.currentTimeMillis();

		//CSV HEADER
		System.out.print("Processing,BBSkylineGUI Cardinality,Tree Height,Page Access,");
		
		//BBS: Index building QueryResult
		System.out.print("BBS - Index building: ");			
		System.out.println(end-start);

		//BBS: Query Processing
				
		for (int i = 0; i < 1; i++) {
			test_a_query (get_Next_query(query_reader), ibl.getTree());
		}
	}
	
	private static void test_a_query(Point_Float Next_query, RTree tree) throws IOException {

		long start, end;
		tree.page_access=0;
		
		Hotel_Sky hsky = new Hotel_Sky(tree, Next_query);

		
		start = System.currentTimeMillis();
		SortedLinList sout = hsky.h_skyline();
		end = System.currentTimeMillis();
		
		System.out.print(end-start + ",");
		tree.load_root();
		System.out.println(sout.get_num()+"," + (tree.root_ptr.level+1) +"," + tree.page_access);
	
		System.gc();

		
		
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
	
	static public void test_a_file (File data_file)
	{
		try {
			
			long start, end;
			
			
			//BBS: Index building
			start = System.currentTimeMillis();
			
			Hotel_Bulk_Loader ibl = new Hotel_Bulk_Loader(data_file);					

			end = System.currentTimeMillis();
			
			System.out.print("BBS - Index building: ");			
			System.out.print(end-start+", ");

			//BBS: Query Processing
			
			Hotel_Sky hsky = new Hotel_Sky(ibl.getTree(), new Point_Float(0, 0));
			System.out.print("Query Processing: ");
			
			start = System.currentTimeMillis();
			SortedLinList sout = hsky.h_skyline();
			end = System.currentTimeMillis();
			
			System.out.print(end-start + ", skyline lenth: ");
			ibl.getTree().load_root();
			System.out.println(sout.get_num()+", tree hegit: " + (ibl.getTree().root_ptr.level+1) +", p.a." + 
			ibl.getTree().page_access);
								
		//	print_all(sout);
			
			sout=null;
			hsky=null;
			
			//Garbage collection
			System.gc();

//			Naive_Loader nl = new Naive_Loader(data_file);
			
			//Navie 1: Data Loading 
			System.out.print("Naive 1 - Data loading: ");


			start = System.currentTimeMillis();
//			LinkedList<Naive_Hotel> list = nl.getHotelList();
			end = System.currentTimeMillis();
			
			System.out.print(end-start + ", ");
			
			//Navie 1: Query Processing:
			System.out.print("Query Processing: ");
			
			start = System.currentTimeMillis();
//			LinkedList<Naive_Hotel> result = (new Naive_Hotel_Skyline(data_file, new Point_Float(0, 0))).skyline();
			end = System.currentTimeMillis();
			
			
			System.out.print(end-start + ", skyline lenth: ");
			
//			System.out.println(result.size());
			
//			result = null;
			//Garbage collection
			System.gc();
			
			
			
			//Navie 2: Data Loading 
			System.out.print("Naive 2 - Data loading: ");

			start = System.currentTimeMillis();
	//		LinkedList<hyun_object> hks_list = nl.get_hkstake_list();
			end = System.currentTimeMillis();
			
			System.out.print(end-start + ", ");
			
			//Navie 2: Query Processing:
			System.out.print("Query Processing: ");
			
			start = System.currentTimeMillis();
			LinkedList<hyun_object> result_2 = (new Hyun_Hotel_Sky(data_file) ).return_skyline_obj();
			end = System.currentTimeMillis();
			

			System.out.print(end-start + ", skyline lenth: ");
			
			System.out.println(result_2.size());
			
	//		print_all(result_2);
			
			System.gc();
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}
}
