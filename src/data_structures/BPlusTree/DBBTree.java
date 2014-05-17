package data_structures.BPlusTree;

import FileUtils.FileManager;
import DBStructure.DBIndex;
import DBStructure.DBRecord;
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
    private ArrayList<String> columnNames;

    public DBBTree(String tableName){
        this.tableName = tableName;
        columnNames = Schema.getColumns(tableName);
    }

    public void insert(int key, DBRecord value) {
        super.insert(key, value);
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

    @Override
    public ArrayList<String> getColumns() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add(columnNames.get(0));
        //TODO primary key doesn't have to be the first
        return columns;
    }

    public void write() {
        String table = FileManager.getTable(tableName);
        String data = commit();
        FileManager.writeToFile(data, table);
    }
}