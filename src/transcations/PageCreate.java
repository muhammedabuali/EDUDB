package transcations;

import operators.CreateOperator;

/**
 * Created by mohamed on 6/22/14.
 */
public class PageCreate extends Step{

    private final CreateOperator create;

    public PageCreate(CreateOperator op){
        this.create = op;
    }

    @Override
    public void execute() {
        create.execute();
    }
}
