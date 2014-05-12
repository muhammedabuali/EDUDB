package DBStructure;

import data_structures.BPlusTree.DBBTree;
import data_structures.BPlusTree.DBBTreeIterator;
import operators.DBResult;
import operators.SelectResult;

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
        indices.add((DBIndex) btree);
    }

    public String getTableName(){
        return tableName;
    }

    public DBIndex getPrimaryIndex(){
        return indices.get(0);
        // TODO primary is at index 0
    }

    public DBResult getData(){
       DBBTree primary = (DBBTree) indices.get(0);
        return new DBBTreeIterator(primary);
    }
}
