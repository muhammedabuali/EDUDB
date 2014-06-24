package operators;

import transcations.Page;
import transcations.Step;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/2/14.
 */
public class ProjectOperator implements Operator{

    /**
     * @uml.property  name="columns"
     * @uml.associationEnd  
     */
    SelectColumns columns;
    /**
     * @uml.property  name="tableDbParameter"
     * @uml.associationEnd  
     */
    DBParameter tableDbParameter;

    public ProjectOperator() {
    }

    @Override
    public DBResult execute(){
        DBResult dbResult = ((Operator) tableDbParameter).execute();
        if(dbResult instanceof DBIterator){
            DBIterator iter = (DBIterator) dbResult;
            iter.project(columns);
            return iter;
        }
        System.out.println("project: 24 not iterator\n");
        return null;
    }

    @Override
    public void print() {
        System.out.print(execute());
    }

    @Override
    public String toString(){
        return "project " + columns.toString()+ " ";
    }
    @Override
    public int numOfParameters() {
        return 2;
    }

    @Override
    public DBParameter[] getChildren() {
        return new DBParameter[] {tableDbParameter};
    }

    @Override
    public void giveParameter(DBParameter par) {
        if(par instanceof SelectColumns)
            columns = (SelectColumns) par;
        else
            tableDbParameter = par;
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
}
