package operators;

/**
 * Created by mohamed on 4/1/14.
 */



public interface Operator extends DBParameter{

    public DBResult execute();
    public DBParameter[] getChildren();
    public void giveParameter(DBParameter par);
}