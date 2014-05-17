package operators;

import data_structures.BPlusTree.DBBTreeIterator;

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

    public FilterOperator() {

    }

    @Override
    public DBResult execute() {
        DBResult dbResult = ((Operator) tableDbParameter).execute();
        if(dbResult instanceof DBBTreeIterator){
            DBBTreeIterator iter = (DBBTreeIterator) dbResult;
            iter.filter(condition);
            return iter;
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
        }else{
            tableDbParameter = par;
        }
    }

}
