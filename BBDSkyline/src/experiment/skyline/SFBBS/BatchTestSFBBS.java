package experiment.skyline.SFBBS;

import experiment.RtreeBulkLoader;
import experiment.skyline.BatchTest;
import experiment.skyline.ExpConfig;
import index.rtree.dimitris.Data;
import index.rtree.dimitris.RTree;
import index.rtree.query.skyline.BBSkyline;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BatchTestSFBBS extends BatchTest {

    public BatchTestSFBBS(String path) throws Exception {
        super(path);
    }

    @Override
    protected void printHeader() {
        System.out.println("dim, cardinality, distribution, response, page_accsess, skyline_cardinality");
    }

    public static void main(String[] args) throws Exception {

        long current = System.currentTimeMillis();

        BatchTestSFBBS tester = new BatchTestSFBBS("data/" + current);

        tester.runTest(

                () -> {

                    try{
                        return getExpConfigs(tester);
                    }catch (Exception ex){
                        System.err.println("Dir Making Error");
                        return null;
                    }

                },

                 config -> {

                     try{

                         RTree tree = RtreeBulkLoader.getTree(config.fileName, "data/temp.r", config.dim,

                                 line -> {

                                     String[] buf = line.split(",");

                                     Data tuple = new Data(config.dim);

                                     tuple.id = Integer.parseInt(buf[0]);

                                     for (int i = 1; i < buf.length ; i++)
                                         tuple.data[2*i-2] = tuple.data[2*i -1] = Float.parseFloat(buf[i]);

                                     return tuple;

                                 });

                         BBSkyline query = new BBSkyline(tree);
                         config.result = query.skyline();

                         System.out.println(config);


                     }catch (Exception ex){
                         System.err.println("Dir Making Error");
                         ex.printStackTrace();
                     }

                     return "fail";
                 }

        );

    }

    private static List<ExpConfig> getExpConfigs(BatchTestSFBBS tester) throws IOException {
        //Create File
        Files.createDirectories(Paths.get(tester.path));

        //Create Configurations
        int[] dims = {4};//, 3, 4, 5, 6};
        int[] cardinalities = {1000}; //, 2000000, 3000000, 4000000, 5000000};

        String[] distributions = {ExpConfig.Anti_RandomStretched_1};

        List<ExpConfig> configs = mkConfigs(dims, cardinalities, distributions);

        configs.forEach(config -> config.makeFile(tester.path));

        return configs;
    }

    private static List<ExpConfig> mkConfigs(int[] dims, int[] cardinalities, String[] distributions) {

        ArrayList<ExpConfig> list = new ArrayList<ExpConfig>();

        for (int dim : dims)
            for (int cardinality : cardinalities)
                for (String distribution : distributions)
                    list.add(new ExpConfig(dim, cardinality, distribution));

        return list;
    }

}
