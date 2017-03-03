package index.rtree.query.skyline;


import index.basic_ds.SortedLinList;
import index.rtree.dimitris.*;
import index.rtree.query.Query;
import index.rtree.query.QueryResult;
import skyline.hotel.global.SKY_Constants;

public class SortFilteredBBS extends Query{

    HeapEntry he; Dir_Entry de; Data_Entry de2;
    private float[] weight;

    public SortFilteredBBS(RTree tree) {
        super(tree);
    }

    private SortedLinList skyline_compute () {

        tree.load_root();
        RTNode root_ptr = tree.root_ptr;
        tree.page_access = 1;
        SortedLinList Result_Set = new SortedLinList();
        SortedLinList heap = new SortedLinList();

        if(tree.root_is_data)
        {
            //TODO
        }

        else{

            RTDirNode node = (RTDirNode) root_ptr;

            float[] mbr = node.get_mbr();

            weight = new float[node.get_dim()];
            for (int i = 0; i < node.get_dim() ; i++)
                weight[i] = (mbr[2*i+1] - mbr[2*i]);

            DirEntry[] entries = node.entries;

            for (int i = 0; i < node.get_num(); i++) {
                DirEntry entry = entries[i];
                heap.insert(new Dir_Entry(entry.get_son(), weight));
            }
        }

        heap.sort();

        while(heap.get_num() > 0)
        {
            HeapEntry obj = (HeapEntry) heap.get_first(); heap.erase();

            if( SKY_Constants.isDominated(obj.mbr, Result_Set)) continue;

            else{

                if(obj instanceof Data_Entry){
                    Data_Entry data_entry = (Data_Entry) obj;
                    data_entry.data.distanz = data_entry.dist;
                    Result_Set.insert(obj);
                    System.out.println("SFBBS, " + ((Data_Entry) obj).data);
                }

                else{

                    tree.page_access++;
                    Dir_Entry entry = (Dir_Entry) obj;
                    if(entry.isDataNode)
                    {
                        RTDataNode node = (RTDataNode)entry.node;

                        Data[] data = node.data;
                        for (int i = 0; i < node.get_num(); i++) {
                            Data child = data[i];

                            if(SKY_Constants.isDominated(child.data, Result_Set) ) continue;

                            else heap.insert(new Data_Entry(child, weight));
                        }

                    }

                    else{

                        RTDirNode node = (RTDirNode)entry.node;

                        DirEntry[] entries = node.entries;
                        for (int i = 0; i < node.get_num(); i++) {

                            DirEntry child = entries[i];

                            if(child.son_is_data)
                            {
                                RTDataNode child_ptr = (RTDataNode) child.get_son();

                                if( SKY_Constants.isDominated(child_ptr.get_mbr(), Result_Set) ) continue;

                                else
                                    heap.insert(new Dir_Entry(child_ptr, weight));
                            }

                            else{

                                RTDirNode child_ptr = (RTDirNode) child.get_son();

                                if( SKY_Constants.isDominated(child_ptr.get_mbr(), Result_Set) ) continue;

                                else
                                    heap.insert(new Dir_Entry(child_ptr, weight));

                            }
                        }

                    }

                    heap.sort();
                }
            }



        }

        Result_Set.sort();

        SortedLinList result = new SortedLinList();

        while(Result_Set.get_num() > 0){

            result.insert(((Data_Entry)Result_Set.get_first()).data);
            Result_Set.erase();
        }

        return result;
    }

	public QueryResult skyline() {

        return retrieveAndMkResult( () -> skyline_compute());

	}

}
