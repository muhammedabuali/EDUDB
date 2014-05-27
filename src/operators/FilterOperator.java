package operators;

import transcations.Page;

/**
 * Created by mohamed on 4/13/14.
 */
public class FilterOperator implements Operator {

    /**
     * @uml.property  name="condition"
     * @uml.associationEnd  
     */
    DBCond condition;
    /**
     * @uml.property  name="tableDbParameter"
     * @uml.associationEnd  
     */
    DBParameter tableDbParameter;
    private DBIterator iterator;

    public FilterOperator() {

    }

    @Override
    public DBResult execute() {
        if (iterator == null){
            DBResult dbResult = ((Operator) tableDbParameter).execute();
            if(dbResult instanceof DBIterator){
                iterator = (DBIterator) dbResult;
            }
        }
        if (iterator != null){
            iterator.filter(condition);
            return iterator;
        }
        System.out.println("filter: not iterator\n");
        return null;
    }

    @Override
    public void print() {
        System.out.print(execute());
    }
    
    @Override
    public String toString(){
        return "filter " + condition.toString()+ " ";
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
        if(par instanceof DBCond){
            condition = (DBCond) par;
        }else if(par instanceof DBResult){
            iterator = (DBIterator) par;
        }else{
            tableDbParameter = par;
        }
    }

    @Override
    public void runStep(Page page) {

    }

    @Override
    public Page getPage() {
        return null;
    }
}
