package DBStructure;

import dataTypes.DB_Type;
import dataTypes.DataType;
import statistics.Schema;
import gudusoft.gsqlparser.nodes.TResultColumnList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohamed on 4/11/14.
 */
public class DBRecord {
    private ArrayList<String> columns;
    private ArrayList<DataType> values;

    public DBRecord(String[] line, String tableName) {
        columns = Schema.getColumns(tableName);
        // TODO remove redundant schema calls
        values = new ArrayList<>();
        for (int i=0; i< line.length; i++){
            // TODO add data types support
            values.add(new DB_Type.DB_Int(Integer.parseInt(line[i])));
        }
    }

    public DBRecord(TResultColumnList RValues, String tableName){
        columns = Schema.getColumns(tableName);
        values = new ArrayList<>();
        for(int i=0; i< RValues.size(); i++){
            values.add( new DB_Type.DB_Int( Integer.parseInt( RValues.getResultColumn(i).toString() ) ) );
        }
    }

    public DataType getValue(int i){
        return values.get(i);
    }

    @Override
    public String toString(){
        String result = values.get(0).toString();
        for(int i=1; i< values.size(); i++){
            result += ',';
            result += values.get(i).toString();
        }
        return  result;
    }
}
