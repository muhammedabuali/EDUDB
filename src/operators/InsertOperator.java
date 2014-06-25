package operators;

import DBStructure.DBIndex;
import DBStructure.DBRecord;
import DBStructure.DBTable;
import DBStructure.DataManager;
import dataTypes.DB_Type;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.nodes.TResultColumnList;
import gudusoft.gsqlparser.stmt.TInsertSqlStatement;
import transcations.*;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/11/14.
 */
public class InsertOperator implements Operator{

    /**
     * @uml.property  name="statement"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    private TInsertSqlStatement statement;
    private String tableName;
    private Page page;

    public InsertOperator(TCustomSqlStatement statement){
        this.statement = (TInsertSqlStatement) statement;
        this.tableName = statement.getTargetTable().toString();
    }

    @Override
    public DBResult execute() {
        System.out.println("executing insert operation");
        PageRead read = new PageRead(this, tableName, true);
        read.execute();
        this.page = read.getPage();
        DBIndex index = (DBIndex) this.page.getTree();

        TResultColumnList values = statement.getValues().getMultiTarget(0).getColumnList();
        DBRecord record = new DBRecord(values, tableName);
        int key = ( (DB_Type.DB_Int) record.getValue(0) ).getNumber();
        index.insert(key, record);
        index.write();
        PageWrite write = new PageWrite(this);
        write.execute();
        return null;
    }

    @Override
    public DBParameter[] getChildren() {
        return new DBParameter[0];
    }

    @Override
    public void giveParameter(DBParameter par) {

    }

    @Override
    public void runStep(Page page) {
        this.page = page;
        DBIndex index = page.getIndex();
        TResultColumnList values = statement.getValues().getMultiTarget(0).getColumnList();
        DBRecord record = new DBRecord(values, tableName);
        int key = ( (DB_Type.DB_Int) record.getValue(0) ).getNumber();
        index.insert(key, record);
        index.write();
    }

    @Override
    public ArrayList<Step> getSteps() {
        ArrayList<Step> out = new ArrayList<>();
        PageRead read = new PageRead(this, tableName, true);
        out.add(read);
        PageWrite write = new PageWrite(this);
        out.add(write);
        return out;
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

    @Override
    public void print() {
        System.out.println(page.getData());
    }

    @Override
    public int numOfParameters() {
        return 0;
    }
}
