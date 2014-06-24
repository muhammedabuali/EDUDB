package transcations;


import operators.DBResult;
import operators.Operator;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by mohamed on 5/20/14.
 */
public class DBTransaction implements Runnable{

    //private ArrayList<Step> vSteps;
    private long ID;
    private Operator operator;

    /*public void init(ArrayList<Step> vSteps){
        this.vSteps = vSteps;
    }*/

    public void init(Operator op){
        this.operator = op;
    }

    @Override
    public void run() {
        /*for (int i=0; i<vSteps.size(); i++){
            Step step = vSteps.get(i);
            step.execute();
        }*/
        operator.execute();
        operator.release();
        operator.print();
    }
}
