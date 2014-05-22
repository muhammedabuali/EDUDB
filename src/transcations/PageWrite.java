package transcations;

import operators.DBParameter;
import operators.DBResult;
import operators.Operator;
import operators.UpdateStep;

/**
 * Created by mohamed on 5/20/14.
 */
public class PageWrite extends Step{
    private Operator operator;

    public PageWrite(Operator operator) {

        this.operator = operator;
    }

    public void giveParameter(DBParameter par){
        operator.giveParameter(par);
    }

    @Override
    public DBResult execute() {
        return null;
    }
}
