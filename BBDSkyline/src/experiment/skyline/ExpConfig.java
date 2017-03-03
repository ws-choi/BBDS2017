package experiment.skyline;

import data.generator3.AntiCorrGenWithC;
import data.generator3.DataGenerator3;
import data.generator3.StretchedGen;
import index.basic_ds.SortedLinList;
import index.rtree.query.QueryResult;

import java.util.HashMap;
import java.util.Map;

public class ExpConfig {

    static Map<Integer, float[]> startPointMap = new HashMap<>();
    static Map<Integer, float[]> weightMap = new HashMap<>();;


    public static final String Anti_RandomStretched_1 = "Anti_RandomStretched_1";
    public static final String Unif_RandomStretched = "Unif_RandomStretched";

    public int dim;
    public int cardinality;
    public String distribution;
    public String fileName;
    public QueryResult result;

    public ExpConfig(int dim, int cardinality, String distribution) {
        this.dim = dim;
        this.cardinality = cardinality;
        this.distribution = distribution;
    }

    public void makeFile(String DirName) {

        this.fileName = DirName + "/" + dim+"_"+cardinality+"_"+distribution +".csv";

        try{

            DataGenerator3 generator = null;

            switch (distribution){

                case Anti_RandomStretched_1:
                    AntiCorrGenWithC innerGenerator = new AntiCorrGenWithC(dim, 1);
                    generator = new StretchedGen(innerGenerator, dim, getStartPointOrRandomPoint(dim), getWeightorRandomWeight(dim));
                    break;

            }

            if(generator != null) generator.AutoFileGen(cardinality, generator, fileName);


        }catch (Exception ex){
            ex.printStackTrace();
        }



    }

    private float[] getWeightorRandomWeight(int dim) {

        if(!weightMap.containsKey(dim))
            weightMap.put(dim, getRandomVector(dim));

        return weightMap.get(dim);
    }

    private float[] getStartPointOrRandomPoint(int dim) {

        if(!startPointMap.containsKey(dim))
            startPointMap.put(dim, getRandomVector(dim));

        return startPointMap.get(dim);

    }

    private float[] getRandomVector(int dim) {

        float[] vec = new float[dim];
        for (int i = 0; i < vec.length; i++) {
            vec[i] = (float) Math.random();
        }
        return vec;
    }

    @Override
    public String toString() {

        StringBuffer res = new StringBuffer();
        res.append(dim);
        res.append(", ");
        res.append(cardinality);
        res.append(", ");
        res.append(distribution);
        res.append(", ");
        res.append(result.toString());
        res.append(", ");
        res.append( ((SortedLinList)result.result).get_num());

        return res.toString();

    }
}
