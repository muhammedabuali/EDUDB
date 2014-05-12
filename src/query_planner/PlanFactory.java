package query_planner;

import gudusoft.gsqlparser.TCustomSqlStatement;
import operators.Operator;

/**
 * Created by mohamed on 4/1/14.
 */
public class PlanFactory implements Planer{
    private Planer planner;

    @Override
    public Operator makePlan(TCustomSqlStatement tCustomSqlStatement) {
        setPlanar(tCustomSqlStatement);
        if (planner == null){
            return null;
        }
        Operator plan = planner.makePlan(tCustomSqlStatement);
        return plan;
    }

   public void setPlanar(TCustomSqlStatement statement){
       System.out.println(statement.sqlstatementtype);
       switch (statement.sqlstatementtype){
           case sstcreatetable:
               planner = new CreateTablePlanner(); break;
           case sstselect:
               planner = new SelectPlanner();break;
           case sstinsert:
               planner= new InsertPlanner();break;
           default:
               System.out.println("Sorry! such statement not supported");
       }
   }
}
