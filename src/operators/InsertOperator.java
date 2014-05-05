package operators;

import DBStructure.DBIndex;
import DBStructure.DBRecord;
import DBStructure.DBTable;
import DBStructure.DataManager;
import edudb_2.data_structures.dataTypes.DB_Type;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.nodes.TMultiTarget;
import gudusoft.gsqlparser.nodes.TMultiTargetList;
import gudusoft.gsqlparser.nodes.TObjectNameList;
import gudusoft.gsqlparser.nodes.TResultColumnList;
import gudusoft.gsqlparser.stmt.TInsertSqlStatement;

/**
 * Created by mohamed on 4/11/14.
 */
public class InsertOperator implements Operator{

    private TInsertSqlStatement statement;

    public InsertOperator(TCustomSqlStatement statement){
        this.statement = (TInsertSqlStatement) statement;
    }

    @Override
    public void execute() {
        System.out.println("executing insert operation");
        DBTable table = DataManager.getTable(statement.getTargetTable().toString());
        if(table == null){
            System.out.println("table doesnot exist");
            return;
        }
        DBIndex index = table.getPrimaryIndex();
        // TODO value may be null
        TResultColumnList values = statement.getValues().getMultiTarget(0).getColumnList();
        DBRecord record = new DBRecord(values, table.getTableName());
        int key = ( (DB_Type.DB_Int) record.getValue(0) ).getNumber();
        index.insert(key, record);
        index.write();
    }

    @Override
    public int numOfParamaters() {
        return 1;
    }

    @Override
    public void giveParameter(Operator relation) {

    }
}
