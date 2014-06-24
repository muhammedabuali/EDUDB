package operators;

import FileUtils.FileManager;
import statistics.Schema;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;
import transcations.Page;
import transcations.PageCreate;
import transcations.Step;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/1/14.
 */
public class CreateOperator implements Operator {

    /**
     * @uml.property  name="statement"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    private TCreateTableSqlStatement statement;

    public CreateOperator(TCreateTableSqlStatement statement){
        this.statement = statement;
    }
    @Override
    public DBResult execute() {
        System.out.println("executing create operation");
        // add table to schema
        String line = statement.getTableName().toString();
        line += " "+ statement.getColumnList().toString();
        System.out.println("@create operation " + line);
        Schema.AddTable(line);
        //create table file and folder
        FileManager.createTable(statement.getTableName().toString());
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

    }

    @Override
    public ArrayList<Step> getSteps() {
        ArrayList<Step> out = new ArrayList<>();
        PageCreate create = new PageCreate(this);
        out.add(create);
        return out;
    }

    @Override
    public Page getPage() {
        return null;
    }

    @Override
    public void release() {

    }


    @Override
    public void print() {

    }

    @Override
    public int numOfParameters() {
        return 0;
    }
}
