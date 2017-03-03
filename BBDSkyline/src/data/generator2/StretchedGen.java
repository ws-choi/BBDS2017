package data.generator2;

import index.rtree.dimitris.Data;

public class StretchedGen extends DataGenerator2 {


    private float[] startPoint;
    private float[] stretchRatio;
    private  DataGenerator2 inputGen;

    public StretchedGen(DataGenerator2 inputGen, int dim, float[] startPoint, float[] stretchRatio) throws Exception {
        super(dim);
        this.inputGen = inputGen;
        this.startPoint = startPoint;
        this.stretchRatio = stretchRatio;
    }

    @Override
    Data getNext() {

        Data data = inputGen.getNext();

        for (int i = 0; i < data.data.length; i++) {
            data.data[i] *= stretchRatio[i];
            data.data[i] += startPoint[i];
        }
        return data;
    }
}
