package operators;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/19/14.
 */
public class AndOperation implements DBMulCondition {

    ArrayList<Operator> parameters;

    public AndOperation() {
        parameters = new ArrayList<Operator>();
    }

    @Override
    public int numOfParameters() {
        return 2;
    }

    @Override
    public void print() {
        System.out.println("AND");
    }

    @Override
    public void giveParameter(DBParameter param) {
        // TODO Auto-generated method stub

    }
}
