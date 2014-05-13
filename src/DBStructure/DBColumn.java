package DBStructure;

import operators.DBParameter;

/**
 * Created by mohamed on 4/19/14.
 */
public class DBColumn implements DBParameter{
    public int order;
    public String tableName;
    public DBColumn(int num, String tableName) {
        this.order = num;
        this.tableName = tableName;
    }
    
    public String toString(){
        return tableName + "." + order;
    }

    @Override
    public void print() {
        System.out.print(tableName + "." + order);
        
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof DBColumn){
            return ((DBColumn) o).order == order
                    && ( (DBColumn) o).tableName.equals(tableName);
        }
        return false;
    }
    @Override
    public int numOfParameters() {
        return 0;
    }
}
