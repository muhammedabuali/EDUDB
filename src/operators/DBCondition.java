package operators;

import DBStructure.DBColumn;

/**
 * Created by mohamed on 4/19/14.
 */
public class DBCondition  implements DBCond{
    DBColumn column1;
    DBColumn column2;
    char op;
    public DBCondition(DBColumn column, DBColumn column2, char op) {
        column1 = column;
        this.column2 = column2;
        this.op = op;
    }

    @Override
    public int numOfParameters() {
        return 0;
    }

    public void print() {
        System.out.print(column1.toString() +" "+op+" " + column2.toString());
    }
    
    @Override
    public String toString(){
        return column1.toString() +" "+op+" " + column2.toString();
    }
}
