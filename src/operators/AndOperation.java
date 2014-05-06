package operators;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/19/14.
 */
public class AndOperation implements DBMulCondition {

    DBCond condition1;
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
}
