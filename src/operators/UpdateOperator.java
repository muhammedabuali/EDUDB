package operators;

import DBStructure.DBRecord;
import data_structures.BPlusTree.DBBTreeIterator;
import transcations.*;

import java.util.ArrayList;

/**
 * Created by mohamed on 5/20/14.
 */
public class UpdateOperator implements Operator{

    private  String tableName;
    private  ArrayList<DBAssignment> assignments;
    private DBCond condition;
    private Page page;

    public UpdateOperator(String tableName, ArrayList<DBAssignment> assignments, DBCondition condition) {
        this.tableName = tableName;
        this.assignments = assignments;
        this.condition = condition;
    }

    @Override
    public DBResult execute() {
        FilterOperator filterOperator = new FilterOperator();
        RelationOperator relationOperator = new RelationOperator(true);
        relationOperator.setTableName(tableName);
        filterOperator.giveParameter(relationOperator);
        filterOperator.giveParameter(condition);
        DBBTreeIterator resultIterator = (DBBTreeIterator) filterOperator.execute();
        this.page = relationOperator.getPage();
        DBRecord record = (DBRecord) resultIterator.first();
        do{
            record.update(assignments);
            record = (DBRecord) resultIterator.next();
        }while (record != null);
        resultIterator.write();
        PageWrite write = new PageWrite(this);
        write.execute();
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
    public void runStep(Page page) {

    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void release() {
        DBBufferManager manager = DBTransactionManager.getBufferManager();
        manager.releasePage(page.getPageId());
    }

    @Override
    public void print() {
        System.out.println(page.getData());
    }

    @Override
    public int numOfParameters() {
        return 0;
    }

    public ArrayList<Step> getSteps() {
        ArrayList<Step> out = new ArrayList<>();

        FilterOperator filterOperator = new FilterOperator();
        RelationOperator relationOperator = new RelationOperator();
        relationOperator.setTableName(tableName);
        filterOperator.giveParameter(relationOperator);
        filterOperator.giveParameter(condition);
        PageRead read = new PageRead(filterOperator, tableName);
        out.add(read);

       // PageWrite write = new PageWrite(updateStep);

        //out.add(write);
        return out;
    }
}
