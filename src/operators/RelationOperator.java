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
    public int numOfParamaters() {
        return 1;
    }

    @Override
    public void giveParameter(Operator relation) {

    }
}
