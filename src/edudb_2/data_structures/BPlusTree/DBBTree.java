package edudb_2.data_structures.BPlusTree;

import edudb_2.FileUtils.FileManager;
import edudb_2.data_structures.DBStructure.DBIndex;
import edudb_2.data_structures.DBStructure.DBRecord;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/11/14.
 */
public class DBBTree extends BTree<Integer, DBRecord> implements DBIndex{
    private String tableName;

    public DBBTree(String tableName){
        this.tableName = tableName;
    }

    public void insert(int key, DBRecord value) {
        this.insert(key, value);
    }

    public void remove(int key) {
        this.delete(key);
    }

    public void readTable(){
        String table = FileManager.getTable(tableName);
        ArrayList<String> lines = FileManager.readFile(table);
        for (int i=0; i< lines.size(); i++){
            String[] line = lines.get(i).split(",");
            if(line.length > 1){
                Integer key = Integer.parseInt(line[0]);
                DBRecord record = new DBRecord(line, tableName);
                this.insert(key, record);
            }
        }
    }
}
