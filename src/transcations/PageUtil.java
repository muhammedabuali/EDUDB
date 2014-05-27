package transcations;

import DBStructure.DBTable;
import operators.DBResult;
import statistics.Schema;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by mohamed on 5/26/14.
 */
public class PageUtil {
    private static HashMap<String, PageID> tables;
    private static boolean initialized;

    public static PageID getPageID(String tableName){
        init();
        PageID id = tables.get(tableName);
        if (id != null)
            return id;
        System.out.println("table " + tableName + " does not exist");
        return null;
    }

    private static void init() {
        if (!initialized){
            initialized = true;
            Set<String> tableNames = Schema.getTableNames();
            tables = new HashMap<>();
            DBBufferManager manager = DBTransactionManager.getBufferManager();
            Iterator iter = tableNames.iterator();
            HashMap<PageID, Page> empty = new HashMap<>();
            while (iter.hasNext()){
                String name = (String) iter.next();
                Page page = new Page(name);
                PageID id = new PageID();
                page.setPageID(id);
                tables.put(name, id);
                empty.put(id, page);
            }
            manager.initEmpty(empty);
        }
    }
}
