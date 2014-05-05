package user_interface;

import query_planner.Plan;
import query_planner.PlanFactory;
import adipe.translate.TranslationException;
import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;

/**
 * Created by mohamed on 4/1/14.
 */
public class Parser {
    TGSqlParser sqlparser;
    PlanFactory planFactory;
    public Parser(){
        sqlparser = new TGSqlParser(EDbVendor.dbvoracle);
        planFactory = new PlanFactory();
    }
    public void parseSQL(String strSQL) throws TranslationException {
        sqlparser.sqltext = strSQL;
        int ret = sqlparser.parse();
        if (ret == 0){
            for(int i=0;i<sqlparser.sqlstatements.size();i++){
                Plan plan = planFactory.makePlan(sqlparser.sqlstatements.get(i));
                System.out.println("plan ready");
                if (plan == null){
                    return;
                }
                plan.execute();
                System.out.println("");
            }
        }else{
            System.out.println(sqlparser.getErrormessage());
        }
    }
}