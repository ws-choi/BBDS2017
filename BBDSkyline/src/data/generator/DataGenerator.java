package data.generator;

import index.rtree.dimitris.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


abstract public class DataGenerator{

	protected int dim;
	protected float bounds[][];
	protected boolean isFloatEnable;

	public DataGenerator(int dim, float[][] bounds, boolean isFloatEnable) throws Exception {
		this.dim = dim;
		setBounds(bounds);
		this.isFloatEnable = isFloatEnable;
	}
	
	public DataGenerator(int dim, Data bounds, boolean isFloatEnable) throws Exception {

		System.out.println(bounds.dimension);
		
		this.dim = dim;
		setBounds(bounds);

		this.isFloatEnable = isFloatEnable;
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


	
	abstract float sample_RV(int dim); // Random Variable
	protected float auto_cast (float input) { return isFloatEnable ? input : (int)input;}	
	
	public Data getNext (){
		Data result = new Data(dim);
		
		for (int i = 0; i < dim; i++) 
			result.data[2*i] = result.data[2*i+1] = sample_RV(i);
		
		return result;
		
	}
	
	public String getNext_String (){

		String result = new String();
		
		for (int i = 0; i < dim; i++) 
		{ 
			if(i+1 == dim)
				result += sample_RV(i) + "\n";
			else
				result += sample_RV(i) + ", ";
					
		}
		
		return result;
		
	}
	
	
	

	public static void main(String[] args) throws Exception {


		int[] data_size = {2097152};
		
		
		float[][] bounds = {
				{0, 10000},
				{0, 10000},
				{0, 10000}

		};
	
		
		//D_uniform Generator
//		auto_file_gen(100, data_size, new UniformGen(3, bounds, true), "UNIF_");

		//D_normal Generator
//		auto_file_gen(data_size, new NormDistGen(3, bounds, true), "NORM_");
				
		//D_anti Generator
		auto_file_gen(data_size, new AntiCorrGen(3, bounds, true, 0, 1), "ANTI_");
//		auto_file_gen(data_size, new Dis_price_Anti(new Point_Float(0, 0), bounds, true), "ANTI_");
								
		
	}

	private static void auto_file_gen(int[] data_size, DataGenerator dg, String string) throws IOException {
		
		Writer writer;
		
		for (int i = 0; i < data_size.length; i++) {
			
			
			File outfile = new File(string + data_size[i]+ "_.txt");
			outfile.delete(); outfile.createNewFile();
				
			writer = new BufferedWriter(new FileWriter(outfile), 1024);

			int id=1;

			writer.write("\"id\", \"x\", \"y\", \"price\"\n");
			
			
			for (int j = 0; j < data_size[i]; j++) 			
				writer.write(id++ +", " + dg.getNext_String());
				
			
			writer.flush();
			writer.close();
			
		}
		
	}
	
	private static void auto_file_gen(double r, int[] data_size, DataGenerator dg, String string) throws IOException {
		
		Writer writer;
		int cnt = 0;
		for (int i = 0; i < data_size.length; i++) {
			
			
			File outfile = new File(string + data_size[i]+ "_.txt");
			outfile.delete(); outfile.createNewFile();
				
			writer = new BufferedWriter(new FileWriter(outfile));

			int id=1;

			writer.write("\"id\", \"x\", \"y\", \"price\"\n");
			
			
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
				System.out.println(cnt++);
			}
				
			
			writer.flush();
			writer.close();
			
		}
		
	}
}
