package operators;

/**
 * Created by mohamed on 4/13/14.
 */
public class RelationOperator implements Operator{

    private String tableName;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void execute() {

    }

    @Override
    public void print() {
        System.out.print("table(" + tableName+ ")");
    }

    @Override
    public int numOfParameters() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Operator getChildren() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void giveParameter(DBParameter par) {
        // TODO Auto-generated method stub
        
    }
}
