package query_planner;

import adipe.translate.sql.Queries;
import edudb_2.statistics.Schema;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TSourceToken;
import gudusoft.gsqlparser.nodes.TJoin;
import gudusoft.gsqlparser.nodes.TJoinList;
import gudusoft.gsqlparser.nodes.TWhereClause;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import query_planner.relational_algebra.Translator;
import ra.Term;

import java.util.ArrayList;
import java.util.HashMap;

import operators.Operator;

/**
 * Created by mohamed on 4/2/14.
 */
public class SelectPlanner implements Planer {

    @Override
    public Plan makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TSelectSqlStatement statement = (TSelectSqlStatement) tCustomSqlStatement;
        ArrayList<Operator> operators = new ArrayList<Operator>();
        // TODO  add a tree of operators
        /*TJoinList fromClause = statement.joins;
        TWhereClause whereClause = statement.getWhereClause();
        TSourceToken selectToken = statement.getSelectToken();*/
        HashMap<String, ArrayList<String>> schema = Schema.getSchema();
        try{

            Translator.translate(statement.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        Plan plan = new Plan(operators);
        return plan;
    }
}
