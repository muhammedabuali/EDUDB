package edudb_2.query_planner;

import edudb_2.operations.CreateOperation;
import edudb_2.operations.Operation;
import edudb_2.statistics.Schema;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.nodes.TColumnDefinitionList;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/1/14.
 */
public class CreateTablePlanner implements Planer {


    @Override
    public Plan makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TCreateTableSqlStatement statement = (TCreateTableSqlStatement) tCustomSqlStatement;
        ArrayList<Operation> operations= new ArrayList<Operation>();
        if(!Schema.chekTableExists(statement.getTableName().toString())){
            Operation operation = new CreateOperation(statement);
            operations.add(operation);
        }
        Plan plan = new Plan(operations);
        return plan;
    }

}
