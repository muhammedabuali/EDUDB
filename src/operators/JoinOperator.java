package operators;

/**
 * Created by mohamed on 4/13/14.
 */
public class JoinOperator implements Operator{
    DBParameter table1;
    DBParameter table2;
    
    @Override
    public void execute() {

    }

    @Override
    public void print() {
        // TODO Auto-generated method stub
        
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
}
