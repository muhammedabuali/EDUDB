package statistics;

import FileUtils.FileManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohamed on 4/1/14.
 */
public class Schema {

    private static HashMap<String, ArrayList<String>> schema;
    private static boolean initalized;

    public static void initSchema(){
        if(initalized){
            return;
        }
        schema = new HashMap<>();
        setSchema();
        initalized = true;
    }

    public static boolean chekTableExists(String tableName){
        initSchema();
        return schema.get(tableName) != null;
    }

    private static void setSchema(){
        ArrayList<String> lines = FileManager.readFile(FileManager.getSchema());
        for(String line : lines){
            putTable(line);
        }
    }

    // get column list of table
    public static ArrayList<String> getColumns(String tableName){
        return schema.get(tableName);
    }

    // add table to schema object
    private static void putTable(String line) {
        String[] tokens = line.split(" ");
        String TableName = tokens[0];
        ArrayList<String> columns = new ArrayList<String>();
        for(int i=1; i<tokens.length; i+=2){
            String columnName = tokens[i];
            columns.add(columnName);
        }
        schema.put(TableName, columns);
    }

    // add table to schema file
    public static void AddTable(String line){
        initSchema();
        putTable(line);
        line += System.lineSeparator();
        System.out.println("new table");
        FileManager.addToFile(FileManager.getSchema(), line);
    }

    public static HashMap<String, ArrayList<String>> getSchema() {
        initSchema();
        return schema;
    }

    public static int getCount(String tableName){
        return schema.get(tableName).size();
    }
}
