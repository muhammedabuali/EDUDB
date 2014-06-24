package operators;

import transcations.Page;
import transcations.Step;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/1/14.
 */



public interface Operator extends DBParameter{

    public DBResult execute();
    public DBParameter[] getChildren();
    public void giveParameter(DBParameter par);

    void runStep(Page page);
    public ArrayList<Step> getSteps();
    Page getPage();

    public void release();
}
