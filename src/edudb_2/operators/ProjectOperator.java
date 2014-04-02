package edudb_2.operators;

import gudusoft.gsqlparser.stmt.TSelectSqlStatement;

/**
 * Created by mohamed on 4/2/14.
 */
public class ProjectOperator implements Operator{

    private TSelectSqlStatement statement;

    public ProjectOperator(TSelectSqlStatement statement){
        this.statement = statement;
    }

    @Override
    public void execute(){

    }
}
