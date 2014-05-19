package DBStructure;

import dataTypes.DB_Type;
import dataTypes.DataType;
import operators.DBCond;
import operators.SelectColumns;
import statistics.Schema;
import gudusoft.gsqlparser.nodes.TResultColumnList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohamed on 4/11/14.
 */
public class DBRecord {
    /**
     * @uml.property  name="columns"
     */
    private ArrayList<DBColumn> columns;
    /**
     * @uml.property  name="values"
     * @uml.associationEnd  multiplicity="(0 -1)" elementType="dataTypes.DB_Type$DB_Int"
     */
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

    public  DBRecord(){
        columns = new ArrayList<>();
        values = new ArrayList<>();
    }

    public void add(ArrayList<DataType> dataValues, ArrayList<DBColumn> dataColumns) {
        for (int i =0; i< dataValues.size(); i++){
            this.values.add(dataValues.get(i));
            this.columns.add(dataColumns.get(i));
        }
    }

    public DataType getValue(int i){
        return values.get(i);
    }

    public String project(SelectColumns selectColumns){
        if (selectColumns == null)
            return toString();
        ArrayList<DBColumn> columnsArray = selectColumns.getColumns();
        String result = "";
        for(int i=0; i< values.size(); i++){
            if (columnsArray.indexOf(columns.get(i)) != -1){
                result += values.get(i).toString();
                result += ',';
            }
        }
        return result;
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

    public String evaluate(ArrayList<DBCond> conditions) {
        if (conditions.isEmpty())
            return this.toString();
        for (int i=0; i< conditions.size(); i++){
            DBCond dbCond = conditions.get(i);
            if (! dbCond.evaluate(this) ){
                return "";
            }
        }
        return this.toString();
    }

    public ArrayList<DataType> getValues() {
        return values;
    }


    public ArrayList<DBColumn> getColumns() {
        return columns;
    }

    public DataType getValue(DBColumn column) {
        int index = columns.indexOf(column);
        return values.get(index);
    }
}
