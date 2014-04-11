package edudb_2.data_structures.DBStructure;

import edudb_2.data_structures.BPlusTree.DBBTree;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/11/14.
 */
public class DBTable {
 //TODO initialize object
    private String tableName;
    private ArrayList<DBIndex> indices;

    public DBTable(String tableName){
        this.tableName = tableName;
        this.indices = new ArrayList<DBIndex>();
        //TODO add other indices
        DBBTree btree = new DBBTree(tableName);
        btree.readTable();
        indices.add(btree);
    }

    public String getTableName(){
        return tableName;
    }

    public DBIndex getPrimaryIndex(){
        return indices.get(0);
        // TODO primary is at index 0
    }
}
