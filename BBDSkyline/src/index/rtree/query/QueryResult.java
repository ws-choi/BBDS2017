package index.rtree.query;

public class QueryResult {

    public long response;
    public int pageAccess;
    public Object result;

    @Override
    public String toString() {
        return response + ", " + pageAccess;
    }
}