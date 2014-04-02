package edudb_2.statistics;

import com.sun.swing.internal.plaf.synth.resources.synth_sv;
import edudb_2.FileUtils.FileManager;

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
            getTable(line);
        }
    }

    private static void getTable(String line) {
        String[] tokens = line.split(" ");
        String TableName = tokens[0];
        ArrayList<String> columns = new ArrayList<String>();
        for(int i=1; i<tokens.length; i+=2){
            String columnName = tokens[i];
            columns.add(columnName);
        }
        schema.put(TableName, columns);
    }

    public static void AddTable(String line){
        initSchema();
        getTable(line);
        line += System.lineSeparator();
        System.out.println("new table");
        FileManager.addToFile(FileManager.getSchema(), line);
    }

    public static HashMap<String, ArrayList<String>> getSchema() {
        initSchema();
        return schema;
    }
}
