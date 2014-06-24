package user_interface;

import DBStructure.DataManager;
import adipe.translate.TranslationException;
import jline.TerminalFactory;
import jline.console.ConsoleReader;
import statistics.Schema;

import java.io.IOException;

public class Main {

    static Thread mainThread;

    public static void main(String[] args) throws TranslationException, InterruptedException {

        mainThread = Thread.currentThread();
        try {
            ConsoleReader console = new ConsoleReader();
            console.setPrompt("edudb2:) ");
            String line;
            Parser parser = new Parser();
            while ((line = console.readLine()) != null) {
                if(line.equals("exit")){
                    System.exit(0);
                }else if(line.equals("clear")){
                    console.clearScreen();
                }else if( line.equals("schema") ){
                    System.out.println(Schema.getString());
                 }else{
                    parser.parseSQL(line);
                    long count = 0;
                    while (count++ < 1000000000){
                        count++;
                    }
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