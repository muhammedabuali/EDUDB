package DBStructure;

import data_structures.BPlusTree.BTree;
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
    /**
     * @uml.property  name="tableName"
     * @uml.associationEnd  qualifier="key:java.lang.String DBStructure.DBTable"
     */
    private String tableName;
    /**
     * @uml.property  name="indices"
     * @uml.associationEnd  multiplicity="(0 -1)" elementType="data_structures.BPlusTree.DBBTree"
     */
    private ArrayList<DBIndex> indices;

    public DBTable(String tableName){
        this.tableName = tableName;
        this.indices = new ArrayList<DBIndex>();
        //TODO add other indices
        DBBTree btree = new DBBTree(tableName);
        btree.readTable();
        indices.add((DBIndex) btree);
    }

    /**
     * @return
     * @uml.property  name="tableName"
     */
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
