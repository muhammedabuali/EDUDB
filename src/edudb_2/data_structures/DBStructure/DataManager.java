package edudb_2.data_structures.DBStructure;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/11/14.
 */
public class DataManager {
    private ArrayList<DBTable> tables;
    public DataManager(){

    }

    public void addTable(DBTable table){
        tables.add(table);
    }
}
