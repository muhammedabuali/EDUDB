package operators;

import edudb_2.data_structures.DBStructure.DBColumn;

/**
 * Created by mohamed on 4/19/14.
 */
public class ConditionOperator  implements Operator{
    public ConditionOperator(DBColumn column, DBColumn column2, char op) {

    }

    @Override
    public void execute() {

    }

    @Override
    public int numOfParamaters() {
        return 1;
    }

    @Override
    public void giveParameter(Operator relation) {

    }
}
