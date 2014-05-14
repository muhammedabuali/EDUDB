package operators;

import DBStructure.DBRecord;

/**
 * Created by mohamed on 4/19/14.
 */
public class OrCondition implements DBMulCondition {

    /**
     * @uml.property  name="condition1"
     * @uml.associationEnd  
     */
    DBCond condition1;
    /**
     * @uml.property  name="condition2"
     * @uml.associationEnd  
     */
    DBCond condition2;

    public int numOfParameters() {
        return 2;
    }

    @Override
    public void print() {
        System.out.println("OR");
    }

    @Override
    public void giveParameter(DBCond param) {
        if (condition1 == null) {
            condition1 = param;
        } else {
            condition2 = param;
        }
    }
    
    public String toString(){
        return "or( " + condition1.toString() + ", " + condition2.toString() +") ";
    }

    @Override
    public DBCond[] getChildren() {
        return new DBCond[] { condition1, condition2 };
    }

    @Override
    public boolean evaluate(DBRecord dbRecord) {
        return condition1.evaluate(dbRecord) || condition2.evaluate(dbRecord);
    }
}
