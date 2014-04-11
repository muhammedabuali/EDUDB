package edudb_2.user_interface;

import adipe.translate.TranslationException;
import jline.TerminalFactory;
import jline.console.ConsoleReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws TranslationException {
//        File sqlFile = new File("C:\\Users\\Azza\\Desktop\\workspace\\edudb\\SQLFile.txt");

        try {
            ConsoleReader console = new ConsoleReader();
            console.setPrompt("edudb> ");
            String line;
            Parser parser = new Parser();
            while ((line = console.readLine()) != null) {
//                if( line.startsWith( "create table") || line.startsWith( "CREATE INDEX")
//                        || line.startsWith( "DROP TABLE") || line.startsWith( "insert into")
//                        || line.startsWith( "delete from") || line.startsWith( "update")
//                        || line.startsWith( "SELECT")){
//
//                    CmdDispatcher.parseSQL(line);
//
//                }
//                if(line.startsWith( "source")){
//
//                    Desktop.getDesktop().open(sqlFile);
//                    console.println(" opening sql fileeeeeee");
//                }
//                if (line.startsWith("ra")){
//                    CmdDispatcher.toRA(line);
//                    continue;
//                }
                if(line.equals("exit")){
                    System.exit(0);
                }else if(line.equals("clear")){
                    console.clearScreen();
                }else if( line.equals("commit") ){
                 }else{
                    parser.parseSQL(line);
                }
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