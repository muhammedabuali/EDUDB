package user_interface;

import gudusoft.gsqlparser.ESqlStatementType;
import operators.Operator;
import query_planner.Plan;
import query_planner.PlanFactory;
import adipe.translate.TranslationException;
import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;

/**
 * Created by mohamed on 4/1/14.
 */
public class Parser {
    /**
     * @uml.property  name="sqlparser"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    TGSqlParser sqlparser;
    /**
     * @uml.property  name="planFactory"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
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
                Operator plan = planFactory.makePlan(sqlparser.sqlstatements.get(i));
                System.out.println("plan ready");
                if (plan == null){
                    return;
                }
                if(sqlparser.sqlstatements.get(i).sqlstatementtype == ESqlStatementType.sstselect)
                    plan.print();
                else
                    System.out.println(plan.execute());
            }
        }else{
            System.out.println(sqlparser.getErrormessage());
        }
    }
}