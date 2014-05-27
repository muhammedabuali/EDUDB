package transcations;

import operators.Operator;

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
    public void execute() {
        PageID pageID = PageUtil.getPageID(tableName);
        DBBufferManager bufferManager = DBTransactionManager.getBufferManager();

        Page read = bufferManager.read(pageID, bModify);
        operator.runStep(read);
    }
}
