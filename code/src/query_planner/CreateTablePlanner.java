package query_planner;

import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

import java.util.ArrayList;

import operators.CreateOperator;
import operators.Operator;
import statistics.Schema;

/**
 * Created by mohamed on 4/1/14.
 */
public class CreateTablePlanner implements Planer {


    @Override
    public Operator makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TCreateTableSqlStatement statement = (TCreateTableSqlStatement) tCustomSqlStatement;
        Operator operator = null;
        if(!Schema.chekTableExists(statement.getTableName().toString())){
            operator = new CreateOperator(statement);
        }else {
            System.out.println("table already exists");
        }
        return operator;
    }

}
