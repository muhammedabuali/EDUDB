package edudb_2.operations;

import edudb_2.FileUtils.FileManager;
import edudb_2.statistics.Schema;
import gudusoft.gsqlparser.nodes.TColumnDefinition;
import gudusoft.gsqlparser.nodes.TColumnDefinitionList;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

/**
 * Created by mohamed on 4/1/14.
 */
public class CreateOperation implements Operation{

    private TCreateTableSqlStatement statement;

    public CreateOperation(TCreateTableSqlStatement statement){
        this.statement = statement;
    }
    @Override
    public void execute() {
        System.out.println("executing");
        // add table to schema
        String line = statement.getTableName().toString();
        line += " "+ statement.getColumnList().toString();
        System.out.println("@create operation " + line);
        Schema.AddTable(line);
        //create table file and folder
        FileManager.createTable(statement.getTableName().toString());

    }
}
