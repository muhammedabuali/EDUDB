package operators;

import DBStructure.DBRecord;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/19/14.
 */
public class AndCondition implements DBMulCondition {

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

    @Override
    public int numOfParameters() {
        return 2;
    }

    @Override
    public void print() {
        System.out.println("AND");
    }

    @Override
    public void giveParameter(DBCond param) {
        if(condition1 == null){
            condition1 = param;
        }else{
            condition2 = param; 
        }
    }

    public String toString(){
        return "and( " + condition1.toString() + ", " + condition2.toString() +") ";
    }
    
    @Override
    public DBCond[] getChildren() {
        return new DBCond[] {condition1, condition2};
    }

    @Override
    public boolean evaluate(DBRecord dbRecord) {
        return condition1.evaluate(dbRecord) && condition2.evaluate(dbRecord);
    }
}
