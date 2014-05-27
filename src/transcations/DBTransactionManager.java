package transcations;

import operators.Operator;
import operators.UpdateOp;
import operators.UpdateOperator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by mohamed on 5/20/14.
 */
public class DBTransactionManager {

    private static DBBufferManager bufferManager;

    public static void init(DBBufferManager manager){
        bufferManager = manager;
    }

    public static DBBufferManager getBufferManager() {
        return bufferManager;
    }

    public static void run(Operator op){
        if (op instanceof UpdateOp){
            updateTable((UpdateOp) op);
        }else {
            op.print();
        }
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

    public static void updateTable(UpdateOp updateOperator){
        ArrayList<Step> steps = updateOperator.getSteps();
        DBTransaction transaction = new DBTransaction();
        transaction.init(steps);
        Thread thread = new Thread(transaction);
        thread.run();
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
