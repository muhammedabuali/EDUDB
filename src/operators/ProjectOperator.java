package operators;

import gudusoft.gsqlparser.stmt.TSelectSqlStatement;

import java.util.ArrayList;

import DBStructure.DBColumn;

/**
 * Created by mohamed on 4/2/14.
 */
public class ProjectOperator implements Operator{

    SelectColumns columns;
    DBParameter tableDbParameter;

    public ProjectOperator() {

    }

    @Override
    public void execute(){

    }

    @Override
    public void print() {
        System.out.print("project " + columns.toString()+ " ");
        tableDbParameter.print();
        
    }

    @Override
    public int numOfParameters() {
        return 2;
    }

    @Override
    public Operator getChildren() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void giveParameter(DBParameter par) {
        if(par instanceof SelectColumns)
            columns = (SelectColumns) par;
        else
            tableDbParameter = par;
    }
}
