package operators;

import DBStructure.DBRecord;
import transcations.Page;
import transcations.Step;

import java.util.ArrayList;

/**
 * Created by mohamed on 5/22/14.
 */
public class UpdateStep implements Operator{
    private DBIterator iterator;
    private ArrayList<DBAssignment> assignments;

    public UpdateStep(ArrayList<DBAssignment> assignments){
        this.assignments = assignments;
    }

    @Override
    public DBResult execute() {
        DBRecord record = (DBRecord) iterator.first();
        do{
            record.update(assignments);
            record = (DBRecord) iterator.next();
        }while (record != null);
        return iterator;
    }

    @Override
    public DBParameter[] getChildren() {
        return new DBParameter[0];
    }

    @Override
    public void giveParameter(DBParameter par) {
        if (par instanceof DBIterator){
            this.iterator = (DBIterator) par;
        }
    }

    @Override
    public void runStep(Page page) {

    }

    @Override
    public ArrayList<Step> getSteps() {
        return null;
    }

    @Override
    public Page getPage() {
        return null;
    }

    @Override
    public void release() {

    }

    @Override
    public void print() {

    }

    @Override
    public int numOfParameters() {
        return 0;
    }
}
