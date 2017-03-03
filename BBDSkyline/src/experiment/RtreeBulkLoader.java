package experiment;

import index.rtree.dimitris.Data;
import index.rtree.dimitris.RTree;

import java.io.*;
import java.util.function.Function;

public final class RtreeBulkLoader {


    private static void deleteIfExists(String filename) throws IOException {

        File f = new File(filename);
        if(f.exists()) f.delete();

    }

    public static RTree getTree (String inputFileName, String outputFilName, int dim, Function<String, Data> mkData) throws IOException {

        return getTree(new File(inputFileName), outputFilName, dim, mkData);
    }


    public static RTree getTree (File inputFile, String outputFilName, int dim,  Function<String, Data> mkData) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        deleteIfExists(outputFilName);

        return bulk_loader(reader, outputFilName, dim, mkData);

    }

    private static RTree bulk_loader(BufferedReader reader, String outputFilName, int dim, Function<String, Data> mkData) throws IOException {

        RTree tree = new RTree(outputFilName, 256, 0, dim);

        String line;

        while( (line = reader.readLine()) != null )
            tree.insert(mkData.apply(line));

        return tree;

    }

}
