package operators;

import statistics.Schema;
import FileUtils.FileManager;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

/**
 * Created by mohamed on 4/1/14.
 */
public class CreateOperator implements Operator {

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
    public void print() {

    }

    @Override
    public int numOfParameters() {
        return 0;
    }
}
