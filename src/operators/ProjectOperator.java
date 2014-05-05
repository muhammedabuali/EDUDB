package operators;

import gudusoft.gsqlparser.stmt.TSelectSqlStatement;

import java.util.ArrayList;

import DBStructure.DBColumn;

/**
 * Created by mohamed on 4/2/14.
 */
public class ProjectOperator implements Operator{

    private TSelectSqlStatement statement;

    public ProjectOperator(TSelectSqlStatement statement){
        this.statement = statement;
    }

    public ProjectOperator() {

    }

    @Override
    public void execute(){

    }

    @Override
    public int numOfParamaters() {
        return 2;
    }

    @Override
    public void giveParameter(Operator relation) {

    }

    public void giveParameter(ArrayList<DBColumn> columns) {
    }
}
