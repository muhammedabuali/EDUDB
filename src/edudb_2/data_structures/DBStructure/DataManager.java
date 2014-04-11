package edudb_2.data_structures.DBStructure;

import edudb_2.statistics.Schema;

import java.util.HashMap;

/**
 * Created by mohamed on 4/11/14.
 */
public class DataManager {
    private static HashMap<String, DBTable > tables;
    private static boolean initialized;

    public void addTable(DBTable table){
        init();// TODO init
        tables.put(table.getTableName(), table);
    }

    public static DBTable getTable(String tableName){
        init();
        if( Schema.chekTableExists(tableName) ){
            Object o = tables.get(tableName);
            if(o != null){
                return (DBTable) o;
            }else{//
                DBTable table = new DBTable(tableName);
                tables.put(tableName, table);
                return table;
            }
        }else{//table doesn't exist
            System.out.println("table " + tableName + " is not in schema");
            return null;
        }
    }

    private static void init() {
        if(!initialized){
            tables = new HashMap<>();
        }
    }

}
