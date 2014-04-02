package edudb_2.query_planner;

import adipe.translate.sql.Queries;
import edudb_2.operations.CreateOperation;
import edudb_2.operations.Operation;
import edudb_2.query_planner.relational_algebra.Translator;
import edudb_2.statistics.Schema;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import ra.Term;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohamed on 4/2/14.
 */
public class SelectPlanner implements Planer {

    @Override
    public Plan makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TSelectSqlStatement statement = (TSelectSqlStatement) tCustomSqlStatement;
        ArrayList<Operation> operations = new ArrayList<Operation>();
        // TODO  add a tree of operations
        HashMap<String, ArrayList<String>> schema = Schema.getSchema();
        try{
            Term relationalAlgebra = Queries.getRaOf(schema, statement.toString());
            Translator.translate(relationalAlgebra.toString());
            System.out.println(relationalAlgebra.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        Plan plan = new Plan(operations);
        return plan;
    }
}
