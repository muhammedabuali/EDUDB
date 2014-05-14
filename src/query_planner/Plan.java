package query_planner;

import java.util.ArrayList;

import operators.Operator;

/**
 * Created by mohamed on 4/1/14.
 */
public class Plan {

    /**
     * @uml.property  name="operators"
     */
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
