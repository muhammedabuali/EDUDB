package query_planner;

import gudusoft.gsqlparser.TCustomSqlStatement;

/**
 * Created by mohamed on 4/1/14.
 */
public interface Planer {
    public Plan makePlan(TCustomSqlStatement tCustomSqlStatement);
}
