package operators;

import DBStructure.DBRecord;
import data_structures.BPlusTree.DBBTreeIterator;

import java.util.ArrayList;

/**
 * Created by mohamed on 5/20/14.
 */
public class UpdateOperator implements Operator{

    private  String tableName;
    private  ArrayList<DBAssignment> assignments;
    private DBCond condition;

    public UpdateOperator(String tableName, ArrayList<DBAssignment> assignments, DBCondition condition) {
        this.tableName = tableName;
        this.assignments = assignments;
        this.condition = condition;
    }

    @Override
    public DBResult execute() {
        FilterOperator filterOperator = new FilterOperator();
        RelationOperator relationOperator = new RelationOperator();
        relationOperator.setTableName(tableName);
        filterOperator.giveParameter(relationOperator);
        filterOperator.giveParameter(condition);
        DBBTreeIterator resultIterator = (DBBTreeIterator) filterOperator.execute();
        DBRecord record = (DBRecord) resultIterator.first();
        do{
            record.update(assignments);
            record = (DBRecord) resultIterator.next();
        }while (record != null);
        resultIterator.write();
        return resultIterator;
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
