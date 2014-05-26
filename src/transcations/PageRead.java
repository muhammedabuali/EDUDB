package transcations;

import operators.DBResult;
import operators.FilterOperator;
import operators.Operator;
import operators.UpdateOp;
import user_interface.Main;

/**
 * Created by mohamed on 5/20/14.
 */
public class PageRead extends Step{
    private Operator operator;
    private String tableName;
    private boolean bModify;

    public PageRead(Operator operator, String tableName) {
        this.operator = operator;
    }

    public PageRead(Operator operator, String tableName, boolean bModify) {
        this.operator = operator;
        this.tableName = tableName;
        this.bModify = bModify;
    }

    @Override
    public DBResult execute() {
        PageID pageID = PageUtil.getPageID(tableName);
        DBBufferManager bufferManager = DBTransactionManager.getBufferManager();
        return bufferManager.read(pageID, bModify);
    }
}
