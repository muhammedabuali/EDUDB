package transcations;


import operators.DBResult;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by mohamed on 5/20/14.
 */
public class DBTransaction implements Runnable{

    private ArrayList<Step> vSteps;
    private long ID;

    public void init(ArrayList<Step> vSteps){
        this.vSteps = vSteps;
    }

    @Override
    public void run() {
        for (int i=0; i<vSteps.size(); i++){
            Step step = vSteps.get(i);
            step.execute();
        }
    }
}
