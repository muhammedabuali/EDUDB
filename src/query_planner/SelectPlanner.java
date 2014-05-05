package query_planner;

import statistics.Schema;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import relational_algebra.Translator;
import java.util.ArrayList;
import java.util.HashMap;

import operators.Operator;

/**
 * Created by mohamed on 4/2/14.
 */
public class SelectPlanner implements Planer {

    @Override
    public Operator makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TSelectSqlStatement statement = (TSelectSqlStatement) tCustomSqlStatement;
        Operator plan ;
        // TODO  add a tree of operators
        HashMap<String, ArrayList<String>> schema = Schema.getSchema();
        try{
            plan = Translator.translate(statement.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        return plan;
    }
}
