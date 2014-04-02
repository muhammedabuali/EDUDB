package edudb_2.FileUtils;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by mohamed on 4/1/14.
 */
public class FileManager {
    private static String dataDirectory ;
    private static String schema;
    private static boolean initialized, isWindows;

    public FileManager(){

    }
    public static ArrayList<String> readFile(String file){
        if(!initialized){
            init();
        }
        ArrayList<String> lines = new ArrayList<>();
        try{
            File dataFile = new  File(file);
            if (!dataFile.exists()){
                dataFile.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void init(){
        if(initialized){
            return;
        }
        dataDirectory = appendToPath(System.getProperty("user.dir"),"database");
        File file = new File(dataDirectory);
        if(!file.exists()){
            file.mkdir();
        }
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        if(operatingSystem.startsWith("windows")){
            isWindows = true;
        }else{
            isWindows = false;
        }
        schema = appendToPath(dataDirectory, "schema.txt");
        initialized = true;
    }

    public static String getSchema() {
        init();
        return schema;
    }

    private static String appendToPath(String S1, String S2){
        if (isWindows){
            return S1+"\\"+S2;
        }else{
            return S1+"/"+S2;
        }
    }
    public static void addToFile(String file, String text) {
        try {
            File dataFile = new  File(file);
            if (!dataFile.exists()){
                dataFile.createNewFile();
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
            output.append(text);
            output.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public static void createTable(String tableName) {
        try {
            String dir = appendToPath(dataDirectory,tableName);
            File dataFile = new  File(dir);
            if (!dataFile.exists()){
                dataFile.mkdir();
                File table = new  File(appendToPath(dir,(tableName+".txt")));
                table.createNewFile();
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
