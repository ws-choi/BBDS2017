package experiment.skyline;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BatchTest {

    protected String path;
    List<ExpConfig> dir;

    public BatchTest (String path) {
        this.path = path;
    }
    protected abstract void printHeader();

    public void runTest(Function<ExpConfig, String> testFunc) {

        dir.forEach(file -> testFunc.apply(file));

    }

    public void runTest(Supplier<List<ExpConfig>> makeFileFunc, Function<ExpConfig, String> testFunc) {

        printHeader();
        dir = makeFileFunc.get();
        runTest(testFunc);

    }






}
