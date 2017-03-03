package index.rtree.query;

import index.basic_ds.SortedLinList;
import index.rtree.dimitris.RTree;

import java.util.function.Supplier;

public class Query {

    protected RTree tree;

    public Query (RTree tree){
        this.tree = tree;
    }

    protected QueryResult retrieveAndMkResult (Supplier<Object> retrieveFunc){

        tree.page_access = 0;

        QueryResult result = new QueryResult ();

        long start = System.currentTimeMillis();
        result.result = retrieveFunc.get();
        long end   = System.currentTimeMillis();

        result.response = end - start;
        result.pageAccess = tree.page_access;

        tree.page_access = 0;

        return result;
    }

}
