package operators;

import DBStructure.DBTable;
import DBStructure.DataManager;
import transcations.*;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/13/14.
 */
public class RelationOp implements Operator{

    /**
     * @uml.property  name="tableName"
     */
    private String tableName;

    /**
     * @param  tableName
     * @uml.property  name="tableName"
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public DBResult execute() {
        DBTable table = DataManager.getTable(tableName);
        return table.getData();
    }

    @Override
    public void print() {
        System.out.print(execute());
    }
    
    @Override
    public String toString(){
        return "table(" + tableName+ ")";
    }
    
    @Override
    public int numOfParameters() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public DBParameter[] getChildren() {
        return new DBParameter[] {};
    }

    @Override
    public void giveParameter(DBParameter par) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ArrayList<Step> getSteps() {
        ArrayList<Step> out = new ArrayList<>();
        PageRead read = new PageRead(this, tableName, false);
        out.add(read);
        return out;
    }

    @Override
    public void runStep(Page page) {
        System.out.println(" thread is running");
        page.print();
        DBBufferManager bufferManager = DBTransactionManager.getBufferManager();
        bufferManager.releasePage(page.getPageId());
    }

    @Override
    public Page getPage() {
        return null;
    }

    @Override
    public void release() {

    }
}
