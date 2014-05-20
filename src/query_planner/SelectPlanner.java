package query_planner;

import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import operators.Operator;
import relational_algebra.Translator;

/**
 * Created by mohamed on 4/2/14.
 */
public class SelectPlanner implements Planer {

    @Override
    public Operator makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TSelectSqlStatement statement = (TSelectSqlStatement) tCustomSqlStatement;
        Operator plan = null;
        // TODO  add a tree of operators
        try{
            plan = Translator.translate(statement.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        return plan;
    }
}
