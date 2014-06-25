package operators;

import DBStructure.DBTable;
import DBStructure.DataManager;
import transcations.*;
import user_interface.Main;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/13/14.
 */
public class RelationOperator implements Operator{

    private final boolean bModify;
    /**
     * @uml.property  name="tableName"
     */
    private String tableName;
    private Page page;

    public RelationOperator(){
        bModify = false;
    }

    public RelationOperator(boolean b) {
        bModify = true;
    }

    /**
     * @param  tableName
     * @uml.property  name="tableName"
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public DBResult execute() {
        PageRead read = new PageRead(this, tableName, bModify);
        read.execute();
        this.page = read.getPage();
        return read.getPage().getData();
    }

    @Override
    public void print() {
        System.out.println(this.page.getData());
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
    public void runStep(Page page) {

    }

    @Override
    public ArrayList<Step> getSteps() {
        return null;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void release() {
        DBBufferManager manager = DBTransactionManager.getBufferManager();
        manager.releasePage(page.getPageId());
    }
}
