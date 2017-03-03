package data.generator3;

import index.rtree.dimitris.Data;

public class StretchedGen extends DataGenerator3 {

    private float[] startPoint;
    private float[] stretchRatio;
    private DataGenerator3 inputGen;

    public StretchedGen(DataGenerator3 inputGen, int dim, float[] startPoint, float[] stretchRatio) throws Exception {

        super(dim);
        this.inputGen = inputGen;
        this.startPoint = startPoint;
        this.stretchRatio = stretchRatio;

    }

    @Override
    Data getNext() {

        Data data = inputGen.getNext();

        for (int i = 0; i < dim; i++) {
            data.data[2*i] *= stretchRatio[i];
            data.data[2*i] += startPoint[i];
            data.data[2*i+1] *= stretchRatio[i];
            data.data[2*i+1] += startPoint[i];
        }

        return data;
    }
}
