package transcations;

import operators.*;

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
        runTransaction(op);
    }

    public static void runTransaction(Operator operator){
        //ArrayList<Step> steps = operator.getSteps();
        //DBTransaction transaction = new DBTransaction();
        //transaction.init(steps);
        DBTransaction transaction = new DBTransaction();
        transaction.init(operator);
        Thread thread = new Thread(transaction);
        thread.start();
    }

    public void createTable(CreateOperator operator)
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

    /*public static void updateTable(UpdateOp updateOperator){
        ArrayList<Step> steps = updateOperator.getSteps();
        DBTransaction transaction = new DBTransaction();
        transaction.init(steps);
        Thread thread = new Thread(transaction);
        thread.run();
    }*/

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
