package transcations;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by mohamed on 5/20/14.
 */
public class DBTransactionManager {

    public void init(){

    }

    public void createTable(String strTableName,
                            Hashtable<String,String> htblColNameType,
                            Hashtable<String,String>htblColNameRefs,
                            String strKeyColName)
            throws DBAppException{

    }

    public void createIndex(String strTableName,
                            String strColName)
            throws DBAppException{

    }

    public void insertIntoTable(String strTableName,
                                Hashtable<String,String> htblColNameValue)
            throws DBAppException{

    }

    public void updateTable(String strTableName,
                            Hashtable<String,String> htblColNameValue)
            throws DBAppException{

    }

    public void deleteFromTable(String strTableName,
                                Hashtable<String,String> htblColNameValue,
                                String strOperator)
            throws DBAppException{

    }

    public Iterator selectFromTable(String strTable,
                                    Hashtable<String,String> htblColNameValue,
                                    String strOperator)
            throws DBAppException{

        return null;
    }

    public void saveAll( ) throws DBEngineException{

    }
}
