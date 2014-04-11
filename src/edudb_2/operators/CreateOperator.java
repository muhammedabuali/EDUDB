package edudb_2.operators;

import edudb_2.FileUtils.FileManager;
import edudb_2.statistics.Schema;
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
}
