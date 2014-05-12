package operators;

/**
 * Created by mohamed on 4/13/14.
 */
public class FilterOperator implements Operator {

    DBCond condition;
    DBParameter tableDbParameter;

    public FilterOperator() {

    }

    @Override
    public DBResult execute() {
        return null;
    }

    @Override
    public void print() {
        System.out.print("filter " + condition.toString()+ " ");
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
