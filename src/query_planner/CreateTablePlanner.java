package query_planner;

import edudb_2.statistics.Schema;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

import java.util.ArrayList;

import operators.CreateOperator;
import operators.Operator;

/**
 * Created by mohamed on 4/1/14.
 */
public class CreateTablePlanner implements Planer {


    @Override
    public Plan makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TCreateTableSqlStatement statement = (TCreateTableSqlStatement) tCustomSqlStatement;
        ArrayList<Operator> operators = new ArrayList<Operator>();
        if(!Schema.chekTableExists(statement.getTableName().toString())){
            Operator operator = new CreateOperator(statement);
            operators.add(operator);
        }else {
            System.out.println("table already exists");
        }
        Plan plan = new Plan(operators);
        return plan;
    }

}
