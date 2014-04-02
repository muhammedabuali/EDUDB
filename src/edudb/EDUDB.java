package edudb;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import adipe.translate.TranslationException;
import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;

import jline.TerminalFactory;
import jline.console.ConsoleReader;

public class EDUDB {
		
	public static void main(String[] args) throws TranslationException{
		
		File sqlFile = new File("C:\\Users\\Azza\\Desktop\\workspace\\edudb\\SQLFile.txt");

        try {
            ConsoleReader console = new ConsoleReader();
            console.setPrompt("edudb> ");
            String line = null;
            while ((line = console.readLine()) != null) {

            	if( line.startsWith( "create table") || line.startsWith( "CREATE INDEX") 
            			|| line.startsWith( "DROP TABLE") || line.startsWith( "insert into")
            		|| line.startsWith( "delete from") || line.startsWith( "update") 
            		|| line.startsWith( "SELECT")){
            		
               		CmdDispatcher.processSQL(line);
            		 
            	}
            	
            	if(line.startsWith( "source")){
            		
            		Desktop.getDesktop().open(sqlFile);
            		console.println(" opening sql fileeeeeee");
            	}

            	if(line.equals("bye")){
            		console.println( "bye bye");
            		System.exit( 0 );
            	}
            	if (line.startsWith("ra")){
                    CmdDispatcher.toRA(line);
                    continue;
                }
            	if(line.equals("cls"))
            		console.clearScreen();
            	else
            		console.println(line);
                
            }
            
            
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                TerminalFactory.get().restore();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}

