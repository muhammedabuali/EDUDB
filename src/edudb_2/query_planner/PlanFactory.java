package edudb_2.query_planner;

import gudusoft.gsqlparser.TCustomSqlStatement;

/**
 * Created by mohamed on 4/1/14.
 */
public class PlanFactory implements Planer{
    private Planer planner;

    @Override
    public Plan makePlan(TCustomSqlStatement tCustomSqlStatement) {
        setPlanar(tCustomSqlStatement);
        if (planner == null){
            return null;
        }
        Plan plan = planner.makePlan(tCustomSqlStatement);
        return plan;
    }

   public void setPlanar(TCustomSqlStatement statement){
       System.out.println(statement.sqlstatementtype);
       switch (statement.sqlstatementtype){
           case sstcreatetable:
               planner = new CreateTablePlanner(); break;
           case sstselect:
               planner = new SelectPlanner();break;
           default:
               System.out.println("Sorry! such statement not supported");
       }
   }
}
