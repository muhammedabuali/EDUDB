package operators;

import transcations.Page;
import transcations.Step;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/13/14.
 */
public class JoinOperator implements Operator{
    /**
     * @uml.property  name="table1"
     * @uml.associationEnd  
     */
    DBParameter table1;
    /**
     * @uml.property  name="table2"
     * @uml.associationEnd  
     */
    DBParameter table2;
    private ProductIterator iterator;

    @Override
    public DBResult execute() {
        DBResult dbResult1 = ((Operator) table1).execute();
        DBResult dbResult2 = ((Operator) table2).execute();
        ProductIterator iter = new ProductIterator();
        iter.giveIterator(dbResult1);
        iter.giveIterator(dbResult2);
        iter.finish();
        this.iterator = iter;
        return iter;
    }

    @Override
    public void print() {
        System.out.print(iterator);
    }

    @Override
    public String toString(){
        return "join";
    }
    @Override
    public int numOfParameters() {
        return 2;
    }

    @Override
    public DBParameter[] getChildren() {
        return new DBParameter[] {table1, table2};
    }

    @Override
    public void giveParameter(DBParameter par) {
        if (table1 ==null){
            table1 = par;
        }else{
            table2 = par;
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
        ( (Operator) table1).release();
        ( (Operator) table2).release();
    }
}
