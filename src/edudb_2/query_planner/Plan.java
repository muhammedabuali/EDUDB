package edudb_2.query_planner;

import edudb_2.operators.Operator;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/1/14.
 */
public class Plan {

    private final ArrayList<Operator> operators;

    public Plan(ArrayList<Operator> operators){
            this.operators = operators;
    }

    public void execute(){
        for(Operator operator : operators){
            operator.execute();
        }
    }
}
