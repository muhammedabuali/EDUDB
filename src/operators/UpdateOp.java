package operators;

import DBStructure.DBRecord;
import data_structures.BPlusTree.DBBTreeIterator;
import transcations.Page;
import transcations.PageRead;
import transcations.PageWrite;
import transcations.Step;

import java.util.ArrayList;

/**
 * Created by mohamed on 5/26/14.
 */
public class UpdateOp implements Operator{

    private  String tableName;
    private ArrayList<DBAssignment> assignments;
    private DBCond condition;
    private Page page;
    private int state;

    public UpdateOp(String tableName, ArrayList<DBAssignment> assignments, DBCondition condition) {
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

    public ArrayList<Step> getSteps() {
        ArrayList<Step> out = new ArrayList<>();
        PageRead read = new PageRead(this, tableName, true);
        out.add(read);
        PageWrite write = new PageWrite(this);
        out.add(write);
        return out;
    }

    @Override
    public void runStep(Page page){
        this.page = page;
        System.out.println("run");
        System.out.println(page);
        FilterOperator filterOperator = new FilterOperator();
        filterOperator.giveParameter(page.getData());
        filterOperator.giveParameter(condition);
        DBIterator iterator = (DBIterator) filterOperator.execute();
        DBRecord record = (DBRecord) iterator.first();
        do{
            record.update(assignments);
            record = (DBRecord) iterator.next();
        }while (record != null);
}

    public Page getPage() {
        return page;
    }

    @Override
    public void release() {

    }
}
