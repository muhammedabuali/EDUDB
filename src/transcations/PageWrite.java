package transcations;

import operators.DBParameter;
import operators.DBResult;
import operators.Operator;
import operators.UpdateStep;

/**
 * Created by mohamed on 5/20/14.
 */
public class PageWrite extends Step{
    private Operator operator;
    private Page page;

    public PageWrite(Operator operator, Page page) {
        this.operator = operator;
        this.page = page;
    }

    public PageWrite(Page page) {
        this.page = page;
    }

    public void giveParameter(DBParameter par){
        operator.giveParameter(par);
    }

    @Override
    public DBResult execute() {
        DBBufferManager bufferManager = DBTransactionManager.getBufferManager();
        bufferManager.write(page.getPageId(), page);
        return null;
    }
}
