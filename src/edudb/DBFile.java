package edudb;

/**
 * Created by mohamed on 3/26/14.
 */
public class DBFile {
    private static final String Directory = System.getProperty("user.dir")+"/data/";

    public static String getDirectory(){
        return Directory;
    }
    public static void main(String[] args){
       System.out.print(DBFile.getDirectory());
    }
}
