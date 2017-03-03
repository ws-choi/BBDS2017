package index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

public class SortTest {
//E:/anti
	
	
	public static void main(String[] args) throws IOException {
		
	
		Comparator<String> cmp = new Comparator<String>() {
			
			@Override
			public int compare(String o1, String o2) {
			
				String[] array_01 = o1.split(",");
				String[] array_02 = o2.split(",");
			
//				float f_01 = Float.parseFloat(array_01[1]) + Float.parseFloat(array_01[2])+ Float.parseFloat(array_01[3])   ;
//				float f_02 = Float.parseFloat(array_02[1]) + Float.parseFloat(array_02[2])+ Float.parseFloat(array_02[3])   ;
				float f_01 = Float.parseFloat(array_01[3])   ;
				float f_02 = Float.parseFloat(array_02[3])   ;
			
				if(f_01 < f_02)
					return -1;
				
				else if (f_01 > f_02)
					return +1;
				
				return 0;
			}
		};
		

		long start = System.currentTimeMillis();

		List<File> a = ExternalSort.sortInBatch(new File("E:/ANTI2_1000000.csv") , cmp);
		
/*
		System.out.println(a.size());
		System.out.println(a.remove(0).getAbsolutePath());
*/	
		ExternalSort.mergeSortedFiles(a, new File("E:/ANTI2_1000000.csv"), cmp);
		long end = System.currentTimeMillis();

		System.out.println(end-start);
		
	}
}
