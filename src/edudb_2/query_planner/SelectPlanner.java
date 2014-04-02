package edudb_2.query_planner;

import edudb_2.operations.CreateOperation;
import edudb_2.operations.Operation;
import edudb_2.statistics.Schema;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/2/14.
 */
public class SelectPlanner implements Planer {

    @Override
    public Plan makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TSelectSqlStatement statement = (TSelectSqlStatement) tCustomSqlStatement;
        ArrayList<Operation> operations = new ArrayList<Operation>();
        // TODO  add a tree of operations
        Plan plan = new Plan(operations);
        return plan;
    }
}
