package edudb_2.data_structures.DBStructure;

import edudb_2.data_structures.dataTypes.DB_Type;
import edudb_2.data_structures.dataTypes.DataType;
import edudb_2.statistics.Schema;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohamed on 4/11/14.
 */
public class DBRecord {
    private ArrayList<String> columns;
    private ArrayList<DataType> values;

    public DBRecord(String[] line, String tableName) {
        HashMap<String,ArrayList<String>> schema = Schema.getSchema();
        columns = schema.get(tableName);
        // TODO remove redundant schema calls
        for (int i=0; i< line.length; i++){
            // TODO add data types support
            values.add(new DB_Type.DB_Int(Integer.parseInt(line[i])));
        }
    }

}
