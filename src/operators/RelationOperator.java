package operators;

import DBStructure.DBTable;
import DBStructure.DataManager;

/**
 * Created by mohamed on 4/13/14.
 */
public class RelationOperator implements Operator{

    private String tableName;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public DBResult execute() {
        DBTable table = DataManager.getTable(tableName);

        return null;
    }

    @Override
    public void print() {
        System.out.print("table(" + tableName+ ")");
    }
    
    @Override
    public String toString(){
        return "table(" + tableName+ ")";
    }
    
    @Override
    public int numOfParameters() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public DBParameter[] getChildren() {
        return new DBParameter[] {};
    }

    @Override
    public void giveParameter(DBParameter par) {
        // TODO Auto-generated method stub
        
    }
}
