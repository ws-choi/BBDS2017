package skyline.hotel.experiment;

import index.basic_ds.SortedLinList;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import skyline.hotel.hotel.Hotel_Sky;

import data.Point_Float;

public class BatchTester extends JFrame{
	
	JButton btnLoadData, btnStartTesting;
	File out;
	File[] data_f;
	
	public BatchTester() {
				
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		btnLoadData = new JButton("Load Data");
		btnLoadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser jfc = new JFileChooser();
//				jfc.setCurrentDirectory(new File("E:/drop/Dropbox/109. My code/LunaWorkspace/Sky_Hotel"));
				jfc.setCurrentDirectory(new File("E:/drop/Dropbox/109. My code/1. LabClibse/Sky_Hotel"));
			
				jfc.setMultiSelectionEnabled(true);
				
				int res = jfc.showOpenDialog(null);
				
				if( res == JFileChooser.APPROVE_OPTION)
				{
					data_f = jfc.getSelectedFiles();
					btnLoadData.setVisible(false);
					btnStartTesting.setVisible(true);
					organize();
					
				}
								
			}
		});
		btnLoadData.setFont(new Font("����", Font.BOLD, 15));
		GridBagConstraints gbc_btnLoadData = new GridBagConstraints();
		gbc_btnLoadData.insets = new Insets(0, 0, 5, 0);
		gbc_btnLoadData.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadData.weightx = 1.0;
		gbc_btnLoadData.gridx = 0;
		gbc_btnLoadData.gridy = 0;
		getContentPane().add(btnLoadData, gbc_btnLoadData);
		

		
		btnStartTesting = new JButton("Start Testing");
		btnStartTesting.setFont(new Font("����", Font.BOLD, 15));
		GridBagConstraints gbc_btnStartTesting = new GridBagConstraints();
		gbc_btnStartTesting.insets = new Insets(0, 0, 5, 0);
		gbc_btnStartTesting.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStartTesting.gridx = 0;
		gbc_btnStartTesting.gridy = 2;
		getContentPane().add(btnStartTesting, gbc_btnStartTesting);
		
		btnStartTesting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Arrays.sort(data_f, new Comparator<File>() {

					@Override
					public int compare(File o1, File o2) {

						int f1 = Integer.parseInt(o1.getName().split("_")[1]);
						int f2 = Integer.parseInt(o2.getName().split("_")[1]);
							
						return f1-f2;
					}
				} );
				
				for (int i = 0; i < data_f.length; i++) {
					System.out.println(data_f[i].getName());
					test_a_file(data_f[i]);
				}
				
			}
		});
		
		init();
	}
			
			
			
			

	public void test_a_file (File data_file)
	{
		try {
			
			long start, end;
			
			
			//BBS: Index building
			
			start = System.currentTimeMillis();
			
			Hotel_Bulk_Loader ibl = new Hotel_Bulk_Loader(data_file);					
			//SortedLinList out = ibl.getTree();
			
			end = System.currentTimeMillis();
			
			System.out.print("BBS - Index building: ");
			
			System.out.print(end-start+", ");

			//BBS: Query Processing
			
//			BBSkylineGUI hsky = new BBSkylineGUI(ibl.getTree(), new Point_Float(0, 0));
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

/*	private void print_all(LinkedList<hyun_object> result_2) {
		
		java.util.Iterator<hyun_object> iter = result_2.iterator();

		while(iter.hasNext())System.out.println(iter.next().oid);
	}

	private void print_all(SortedLinList sout) {
		
		while(sout.get_num() > 0){
			
			
			Hotel_Entry entry = (Hotel_Entry)sout.get(0);
			
			System.out.println(entry.data.id);
			
			sout.erase();
		}
		
	}*/

	
	private void init() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		btnStartTesting.setVisible(false);
		setVisible(true);
		organize();
		
		
	}
	
	private void organize() {
		setLocationRelativeTo(null);
		pack();
		
	}

	public static void main(String[] args) {
		new BatchTester() ;
	}


	private static final long serialVersionUID = 953740250491778303L;


}
