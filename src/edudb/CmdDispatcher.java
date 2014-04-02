package edudb;

import adipe.translate.TranslationException;
import adipe.translate.sql.Queries;
import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;
import ra.Term;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CmdDispatcher {
	
	public static Hashtable processSQL( String strSQL) throws TranslationException {
		if(strSQL.startsWith("SELECT")){

            CmdDispatcher.toRA(strSQL);
        }
		TGSqlParser sqlparser = new TGSqlParser(EDbVendor.dbvoracle);
		sqlparser.sqltext = strSQL;
		int ret = sqlparser.parse();
		if (ret == 0){
		for(int i=0;i<sqlparser.sqlstatements.size();i++){
		AnalyzeSQLStmt.analyzeStmt(sqlparser.sqlstatements.get(i));
		System.out.println("");
		}
		}else{
		System.out.println(sqlparser.getErrormessage());
		}
		
		return null;
	}
	
	public static Hashtable processSQLFile( String strSQLFileFullPath )throws TranslationException{
		
		String sqltext = "";
		
		try{
			
			FileInputStream stream = new FileInputStream(strSQLFileFullPath);
			DataInputStream in1 = new DataInputStream(stream);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String strLine;
			
			while ((strLine = br1.readLine()) != null ){
			
				sqltext = sqltext+strLine;
				
			}
			br1.close();
			System.out.println(sqltext);
			
		}catch(Exception e){
			
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		processSQL(sqltext);
		return null;
	}

	public static void main(String args[])throws TranslationException{
		
		processSQLFile("C:\\Users\\Azza\\Desktop\\workspace\\edudb\\SQLFile.txt");

	}

    public static void toRA(String line) throws TranslationException {
        System.out.println(line);
        System.out.println(RA.db_schema == null);
        System.out.println(RA.db_schema.toString());
        try{
            Term raOf = Queries.getRaOf(RA.db_schema, line);
            System.out.println(raOf.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
