package edudb;

/**
 * Created by mohamed on 3/26/14.
 */
import adipe.translate.*;
import adipe.translate.sql.Queries;
import ra.Term;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
public class RA {
    public static Map<String,ArrayList<String>> db_schema;

    public static void main(String[] args) throws TranslationException{
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        Map<String,ArrayList<String>> schema = new HashMap<String, ArrayList<String>>();
        schema.put("p", new ArrayList<String>(Arrays.asList("a", "b", "p")));
        schema.put("1", new ArrayList<String>(Arrays.asList("a")));
        schema.put("xy", new ArrayList<String>(Arrays.asList("x", "y")));
        schema.put("r4", new ArrayList<String>(Arrays.asList("ra", "rb", "rc", "rd")));
        Term raOf = Queries.getRaOf(schema, "SELECT min(ra),sum(rd),rc,rb FROM r4 GROUP BY rb,rc");
        System.out.println(raOf.toString());
    }

}
