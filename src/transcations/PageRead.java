package transcations;

import operators.DBResult;
import operators.FilterOperator;
import operators.Operator;

/**
 * Created by mohamed on 5/20/14.
 */
public class PageRead extends Step{
    private Operator operator;

    public PageRead(Operator operator, String tableName) {
        this.operator = operator;
    }

    @Override
    public DBResult execute() {
        DBResult result = operator.execute();
        return result;
    }
}
