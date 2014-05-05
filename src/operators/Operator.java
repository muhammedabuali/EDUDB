package operators;

/**
 * Created by mohamed on 4/1/14.
 */



public interface Operator {

    public void execute();
    public Operator getChildren();
    public void print();
}
