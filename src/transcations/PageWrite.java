package transcations;

import operators.DBParameter;
import operators.Operator;

/**
 * Created by mohamed on 5/20/14.
 */
public class PageWrite extends Step{
    private Operator operator;

    public PageWrite(Operator operator) {
        this.operator = operator;
    }


    public void giveParameter(DBParameter par){
        operator.giveParameter(par);
    }

    @Override
    public void execute() {
        Page page = operator.getPage();
        DBBufferManager bufferManager = DBTransactionManager.getBufferManager();
        bufferManager.write(page.getPageId(), page);
    }
}
