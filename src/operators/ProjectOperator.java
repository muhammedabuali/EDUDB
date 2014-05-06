package operators;

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
    }

    @Override
    public String toString(){
        return "project " + columns.toString()+ " ";
    }
    @Override
    public int numOfParameters() {
        return 2;
    }

    @Override
    public DBParameter getChildren() {
        return tableDbParameter;
    }

    @Override
    public void giveParameter(DBParameter par) {
        if(par instanceof SelectColumns)
            columns = (SelectColumns) par;
        else
            tableDbParameter = par;
    }
}
