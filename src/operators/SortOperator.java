package operators;

import transcations.Page;

/**
 * Created by mohamed on 4/13/14.
 */
public class SortOperator implements Operator{
    @Override
    public DBResult execute() {
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
    public void runStep(Page page) {

    }

    @Override
    public Page getPage() {
        return null;
    }

    @Override
    public void print() {

    }

    @Override
    public int numOfParameters() {
        return 0;
    }
}
