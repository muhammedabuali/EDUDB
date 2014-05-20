package transcations;

import java.util.Hashtable;

/**
 * Created by mohamed on 5/20/14.
 */
public class DBLogManager {

    public void init( ){

    }

    public synchronized void flushLog( ){

    }

    public synchronized void recordStart( String strTransID ){

    }

    public synchronized void recordUpdate(	  String strTransID,
                                                PageID page,
                                                String strKeyValue,
                                                String strColName,
                                                Object objOld,
                                                Object objNew){

    }

    public synchronized void recordInsert(String strTransID,
                                          PageID page,
                                          Hashtable<String,String> htblColValues){

    }

    public synchronized void recordDelete(String strTransID,
                                          PageID page,
                                          String strKeyValue,
                                          Hashtable<String,String> htblColValues){

    }

    public synchronized void recordCommit( String strTransID ){

    }
}
