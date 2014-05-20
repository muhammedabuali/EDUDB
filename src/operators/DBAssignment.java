package operators;

import DBStructure.DBRecord;
import dataTypes.DB_Type;
import dataTypes.DataType;
import statistics.Schema;

/**
 * Created by mohamed on 5/20/14.
 */
public class DBAssignment implements DBParameter{

    private DataType value;
    private int order;

    public DBAssignment(String s1, String s2, String tableName) {
        this.order = Schema.getColumnNumber(s1, tableName);
        this.value = new DB_Type.DB_Int(s2);
    }

    @Override
    public void print() {

    }

    @Override
    public int numOfParameters() {
        return 0;
    }

    public void execute(DBRecord dbRecord) {
        dbRecord.setValue(order, value);
    }
}
