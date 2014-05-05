package operators;

import statistics.Schema;
import edudb_2.FileUtils.FileManager;
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
    public void execute() {
        System.out.println("executing create operation");
        // add table to schema
        String line = statement.getTableName().toString();
        line += " "+ statement.getColumnList().toString();
        System.out.println("@create operation " + line);
        Schema.AddTable(line);
        //create table file and folder
        FileManager.createTable(statement.getTableName().toString());

    }

    @Override
    public int numOfParamaters() {
        return 1;
    }

    @Override
    public void giveParameter(Operator relation) {

    }
}
