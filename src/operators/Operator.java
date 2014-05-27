package operators;

import transcations.Page;

/**
 * Created by mohamed on 4/1/14.
 */



public interface Operator extends DBParameter{

    public DBResult execute();
    public DBParameter[] getChildren();
    public void giveParameter(DBParameter par);

    void runStep(Page page);

    Page getPage();
}
