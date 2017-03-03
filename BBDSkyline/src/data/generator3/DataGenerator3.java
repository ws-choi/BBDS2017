package data.generator3;

import index.rtree.dimitris.Data;

import java.io.*;


abstract public class DataGenerator3 {


    protected int dim;

    public DataGenerator3(int dim)
    {
        this.dim = dim;
    }

    abstract Data getNext ();

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


   public static void AutoFileGen(int cardinality, DataGenerator3 generator, String path) throws IOException {

       Writer writer;

       File outfile = new File(path);

       outfile.delete();
       outfile.createNewFile();

       writer = new BufferedWriter(new FileWriter(outfile), 1024);


       int id=1;

       for (int j = 0; j < cardinality; j++)
           writer.write(id++ +", " + generator.getNext_String());

       writer.flush();
       writer.close();

   }


}
