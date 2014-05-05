package query_planner;

import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.nodes.TMultiTargetList;
import gudusoft.gsqlparser.nodes.TObjectNameList;
import gudusoft.gsqlparser.stmt.TInsertSqlStatement;

import java.util.ArrayList;

import operators.InsertOperator;
import operators.Operator;

/**
 * Created by mohamed on 4/9/14.
 */
public class InsertPlanner implements Planer {
    @Override
    public Plan makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TInsertSqlStatement statement = (TInsertSqlStatement) tCustomSqlStatement;
        //TODO read column list
        TMultiTargetList values = statement.getValues();
        System.out.println(values.toString());
        Operator insert = new InsertOperator(tCustomSqlStatement);
        ArrayList<Operator> operators = new ArrayList<>();
        operators.add(insert);
        Plan plan = new Plan(operators);
        return plan;
    }
}
