package operators;

import DBStructure.DBIndex;
import DBStructure.DBRecord;
import DBStructure.DBTable;
import DBStructure.DataManager;
import dataTypes.DB_Type;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.nodes.TResultColumnList;
import gudusoft.gsqlparser.stmt.TInsertSqlStatement;

/**
 * Created by mohamed on 4/11/14.
 */
public class InsertOperator implements Operator{

    /**
     * @uml.property  name="statement"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    private TInsertSqlStatement statement;

    public InsertOperator(TCustomSqlStatement statement){
        this.statement = (TInsertSqlStatement) statement;
    }

    @Override
    public DBResult execute() {
        System.out.println("executing insert operation");
        DBTable table = DataManager.getTable(statement.getTargetTable().toString());
        if(table == null){
            System.out.println("table doesnot exist");
            return null;
        }
        DBIndex index = table.getPrimaryIndex();
        // TODO value may be null
        TResultColumnList values = statement.getValues().getMultiTarget(0).getColumnList();
        DBRecord record = new DBRecord(values, table.getTableName());
        int key = ( (DB_Type.DB_Int) record.getValue(0) ).getNumber();
        index.insert(key, record);
        index.write();
        return null;
    }

    @Override
    public DBParameter[] getChildren() {
        return new DBParameter[0];
    }

    @Override
    public void giveParameter(DBParameter par) {

    }

    @Override
    public void print() {

    }

    @Override
    public int numOfParameters() {
        return 0;
    }
}
