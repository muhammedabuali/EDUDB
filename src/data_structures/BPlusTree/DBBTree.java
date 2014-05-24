package data_structures.BPlusTree;

import DBStructure.DBColumn;
import FileUtils.FileManager;
import DBStructure.DBIndex;
import DBStructure.DBRecord;
import dataTypes.DB_Type;
import operators.DBIterator;
import statistics.Schema;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohamed on 4/11/14.
 */
public class DBBTree extends BTree<Integer, DBRecord> implements DBIndex{
    /**
     * @uml.property  name="tableName"
     * @uml.associationEnd  
     */
    private String tableName;
    /**
     * @uml.property  name="columnNames"
     */
    private ArrayList<DBColumn> columnNames;

    public DBBTree(String tableName){
        this.tableName = tableName;
        columnNames = Schema.getColumns(tableName);
    }

    public void insert(int key, DBRecord value) {
        super.insert(key, value);
    }

    @Override
    public DBIterator getIterator() {
        return new DBBTreeIterator(this);
    }

    @Override
    public DBIndex getCopy() {
        DBBTree out = new DBBTree(tableName);
        DBIterator iter = getIterator();
        DBRecord record = (DBRecord) iter.first();
        do{
            out.insert( ( (DB_Type.DB_Int)record.getValue(0) ).getNumber() , record.getCopy());
            record = (DBRecord) iter.next();
        }while (record != null);
        return out;
    }

    public void remove(int key) {
        super.delete(key);
    }

    public void readTable(){
        String table = FileManager.getTable(tableName);
        ArrayList<String> lines = FileManager.readFile(table);
        for (int i=0; i< lines.size(); i++){
            String[] line = lines.get(i).split(",");
            Integer key = Integer.parseInt(line[0]);
            DBRecord record = new DBRecord(line, tableName);
            this.insert(key, record);
        }
    }

    public ArrayList<DBColumn> getColumns() {
        return columnNames;
    }

    public void write() {
        String table = FileManager.getTable(tableName);
        String data = commit();
        FileManager.writeToFile(data, table);
    }

    public static void main(String [] args){
        DBBTree tree = new DBBTree("persons");
        tree.readTable();
        DBBTree copy = (DBBTree) tree.getCopy();
        System.out.println("tree");
        tree.print();
        System.out.println("copy");
        copy.print();
        ( (DBRecord)copy.getSmallest().getValue(0) ).setValue(0, new DB_Type.DB_Int(5));
        System.out.println("tree");
        tree.print();
        System.out.println("copy");
        copy.print();
    }
}
