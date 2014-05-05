package operators;

import DBStructure.DBColumn;

/**
 * Created by mohamed on 4/19/14.
 */
public class DBCondition  implements DBParameter{
    public DBCondition(DBColumn column, DBColumn column2, char op) {

    }

    @Override
    public int numOfParameters() {
        return 0;
    }

    public void print() {
        // TODO Auto-generated method stub
        
    }
}
