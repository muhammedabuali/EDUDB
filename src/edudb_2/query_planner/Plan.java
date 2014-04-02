package edudb_2.query_planner;

import edudb_2.operations.Operation;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/1/14.
 */
public class Plan {

    private final ArrayList<Operation> operations;

    public Plan(ArrayList<Operation> operations){
            this.operations = operations;
    }

    public void execute(){
        for(Operation operation: operations){
            operation.execute();
        }
    }
}
