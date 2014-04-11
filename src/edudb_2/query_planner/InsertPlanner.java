package edudb_2.query_planner;

import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.nodes.TMultiTargetList;
import gudusoft.gsqlparser.nodes.TObjectNameList;
import gudusoft.gsqlparser.stmt.TInsertSqlStatement;

/**
 * Created by mohamed on 4/9/14.
 */
public class InsertPlanner implements Planer {
    @Override
    public Plan makePlan(TCustomSqlStatement tCustomSqlStatement) {
        TInsertSqlStatement statement = (TInsertSqlStatement) tCustomSqlStatement;
        TObjectNameList columns = statement.getColumnList();
        TMultiTargetList values = statement.getValues();
        System.out.println(columns.toString());
        System.out.println(values.toString());
        return null;
    }
}
