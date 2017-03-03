package skyline.hotel.experiment2;

import index.basic_ds.SortedLinList;
import index.rtree.dimitris.RTree;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import data.Point_Float;

public class BatchTester {

	public static void main(String[] args) throws IOException {
		
		
		
		File folder = new File("D:/Dropbox/109. My code/1. LabClibse/Sky_Hotel_Final/Test");
		
		File[] files = folder.listFiles();
		
		System.out.println("filename, Algorithm,skyline_cardinality,processing_time,page_access");
		for (int i = 0; i < files.length; i++) {
			
			test_a_file(files[i], new Point_Float(5000, 5000));
			
		}
		
	
		
	}

	private static void test_a_file(File file, Point_Float Next_query) throws IOException {
		
		long start_,finish;
		LinkedList<Hotel_object> bnl_out;
		SortedLinList bbs_out;
		RTree tree ;

		/******************************************************************************/
		/*********************************    BBS    **********************************/
/*		System.gc();
		
		tree = (new Hotel_Bulk_Loader(file)).getTree(); tree.page_access = 0;
		start_ = System.currentTimeMillis();
		bbs_out = (new Hotel_Sky(tree,  Next_query)).h_skyline(); //BBS
		finish = System.currentTimeMillis();

		print_result(file, "BBS", bbs_out, finish-start_, tree.page_access);
		
		bnl_out = null; bbs_out=null; tree=null;*/		
		/*********************************    BBS    **********************************/
		/******************************************************************************/
		
			
		/******************************************************************************/
		/********************************* LESS (BNL)**********************************/

		
		System.gc();
		
//		if(!file.getName().contains("ANTI"))
		{
		start_ = System.currentTimeMillis();
		bnl_out = (new Dynamic_LESS_for_Hotel(file) ).return_skyline_obj(Next_query);			//LESS (BNL)
		
		finish = System.currentTimeMillis();
		 
		print_result(file, "LESS",  bnl_out, finish-start_);
		bnl_out = null; bbs_out=null; tree=null;
		}
		
		
		/********************************* LESS (BNL)**********************************/
		/******************************************************************************/

		
		/******************************************************************************/
		/********************************* ANTI_LESS **********************************/
/*		System.gc();

		start_ = System.currentTimeMillis();
		bnl_out = (new Dynamic_ANTILESS_for_Hotel(file) ).return_skyline_obj(Next_query);		//ANTI LESS
		finish = System.currentTimeMillis();

		print_result(file, "ANTI_LESS",  bnl_out, finish-start_);
		bnl_out = null; bbs_out=null; tree=null;*/
		/********************************* ANTI_LESS **********************************/
		/******************************************************************************/		
		
	}

	private static void print_result(File file, String string,
			SortedLinList bbs_out, long l, int page_access) {
		System.out.println(file.getName() + "," + string + "," + bbs_out.get_num()  + "," + l + "," + page_access);
		
	}

	private static void print_result(File file, String string, LinkedList<Hotel_object> bnl_out, long l) {
		
		System.out.println(file.getName() + "," + string  + "," + bnl_out.size()  + "," + l);

		
	}
}
