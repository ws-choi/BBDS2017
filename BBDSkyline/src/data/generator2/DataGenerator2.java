package data.generator2;

import index.rtree.dimitris.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


 abstract public class DataGenerator2{

	protected int dim;
	protected float bounds[][];
	protected boolean isFloatEnable;


	public DataGenerator2(int dim, float[][] bounds, boolean isFloatEnable) throws Exception {
		this.dim = dim;
		setBounds(bounds);
		this.isFloatEnable = isFloatEnable;
	}
	
	public DataGenerator2(int dim, Data bounds, boolean isFloatEnable) throws Exception {

		System.out.println(bounds.dimension);
		
		this.dim = dim;
		setBounds(bounds);

		this.isFloatEnable = isFloatEnable;
	}

	 public DataGenerator2(int dim) {
		 this.dim = dim;
	 }

	 private void setBounds (float[][] bounds) throws Exception {
		
		if (bounds.length != dim)	
			throw new Exception();
			
		for (int i = 0; i < bounds.length; i++)
			if(bounds[i][0] > bounds[i][1])
				throw new Exception();
		
		this.bounds = bounds.clone();
	}
	
	private void setBounds (Data data) throws Exception {
		
		if (data.dimension != dim)	
			throw new Exception();
		
		bounds= new float[dim][2];
		
		for (int i = 0; i < bounds.length; i++)
		{
			bounds[i][0] = data.data[2*i];
			bounds[i][1] = data.data[2*1+1];
		}
		
	}


	
	protected float auto_cast (float input) { return isFloatEnable ? input : (int)input;}	
	
	abstract Data getNext ();
	//{
	/*	Data result = new Data(dim);
		
		for (int i = 0; i < dim; i++) 
			result.data[2*i] = result.data[2*i+1] = sample_RV(i);
		
		return result;
		
	}
	*/
	public String getNext_String (){

		String result = new String();

		Data data = getNext();
		for (int i = 0; i < dim; i++) 
		{ 
			if(i+1 == dim)
				result += data.data[2*i] + "\n";
			else
				result += data.data[2*i] + ", ";
					
		}
		
		return result;
		
	}
	
	
	

	public static void main(String[] args) throws Exception {


		int[] data_size = { 7000000 };
//		int[] data_size = {10000 };
//		int[] data_size = {100000, 100001,100002,100003,100004 };
		
		
		float[][] bounds = {
/*				{0, 10000},
				{0, 10000},
				{0, 10000}*/
				
				{0,1}, {0,1}

		};
	
		
		//D_uniform Generator
		auto_file_gen(data_size, new Uniform_2_DG(2, bounds, true), "UNIF_");

		//D_normal Generator
//		auto_file_gen(data_size, new NormDistGen(3, bounds, true), "NORM_");
				
		//D_anti Generator
//		auto_file_gen(data_size, new Correlated_Hotel_DG(bounds, true, 1), "ANTI_1_");
//		auto_file_gen(data_size, new Correlated_Hotel_DG(bounds, true, 0.1), "ANTI_0.1_");
//		auto_file_gen(data_size, new Correlated_Hotel_DG(bounds, true, 0.01), "ANTI_0.01_");
//		auto_file_gen(data_size, new Dis_price_Anti(new Point_Float(0, 0), bounds, true), "ANTI_");
								
		
	}

	private static void auto_file_gen(int[] data_size, DataGenerator2 dg, String string) throws IOException {
		
		Writer writer;
		
		for (int i = 0; i < data_size.length; i++) {
			
			
			File outfile = new File(string + data_size[i]+ ".csv");
			outfile.delete(); outfile.createNewFile();
				
			writer = new BufferedWriter(new FileWriter(outfile), 1024);

			int id=1;

//			writer.write("\"id\", \"x\", \"y\", \"price\"\n");
			
			
			for (int j = 0; j < data_size[i]; j++) 			
				writer.write(id++ +", " + dg.getNext_String());
				
			
			writer.flush();
			writer.close();
			
		}
		
	}
	
	private static void auto_file_gen(double r, int[] data_size, DataGenerator2 dg, String string) throws IOException {
		
		Writer writer;
//		int cnt = 0;
		for (int i = 0; i < data_size.length; i++) {
			
			
			File outfile = new File(string + data_size[i]+ ".csv");
			outfile.delete(); outfile.createNewFile();
				
			writer = new BufferedWriter(new FileWriter(outfile));

			int id=1;

//			writer.write("\"id\", \"x\", \"y\", \"price\"\n");
			
			
			for (int j = 0; j < data_size[i]; j++) 		{
				
				Data data = dg.getNext();
				double length = 0;
				for (int k = 0; k < data.dimension; k++) {
					length += data.data[2*k] * data.data[2*k] ;
				}
				length = Math.sqrt(length);

				if(length > r){
					j--;
					continue;
				}
				writer.write(id++ +", " + data.data[0] + ", " + data.data[2] + ", " +data.data[4] +"\n" );
//				System.out.println(cnt++);
			}
				
			
			writer.flush();
			writer.close();
			
		}
		
	}
}
