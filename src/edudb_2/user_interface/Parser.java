package edudb_2.user_interface;

import adipe.translate.TranslationException;
import edudb_2.query_planner.Plan;
import edudb_2.query_planner.PlanFactory;
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
                plan.execute();
                System.out.println("");
            }
        }else{
            System.out.println(sqlparser.getErrormessage());
        }
    }
//    public static Hashtable processSQLFile( String strSQLFileFullPath )throws TranslationException{
//        String sqltext = "";
//        try{
//            FileInputStream stream = new FileInputStream(strSQLFileFullPath);
//            DataInputStream in1 = new DataInputStream(stream);
//            BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
//            String strLine;
//            while ((strLine = br1.readLine()) != null ){
//                sqltext = sqltext+strLine;
//            }
//            br1.close();
//            System.out.println(sqltext);
//        }catch(Exception e){
//            System.err.println("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//        parseSQL(sqltext);
//        return null;
//    }

//    public static void main(String args[])throws TranslationException{
//
//        processSQLFile("C:\\Users\\Azza\\Desktop\\workspace\\edudb\\SQLFile.txt");
//
//    }

//    public static void toRA(String line) throws TranslationException {
//        System.out.println(line);
//        System.out.println(RA.db_schema == null);
//        System.out.println(RA.db_schema.toString());
//        try{
//            Term raOf = Queries.getRaOf(RA.db_schema, line);
//            System.out.println(raOf.toString());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
