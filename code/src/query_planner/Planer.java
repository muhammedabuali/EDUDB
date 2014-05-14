package query_planner;

import gudusoft.gsqlparser.TCustomSqlStatement;
import operators.Operator;

/**
 * Created by mohamed on 4/1/14.
 */
public interface Planer {
    public Operator makePlan(TCustomSqlStatement tCustomSqlStatement);
}
