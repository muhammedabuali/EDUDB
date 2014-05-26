package transcations;


import operators.DBResult;

import java.util.Vector;

/**
 * Created by mohamed on 5/20/14.
 */
public class DBTransaction implements Runnable{

    private DBBufferManager bufManager;
    private DBLogManager logManager;
    private Vector<Step> vSteps;
    private long ID;
    public void init( DBBufferManager bufManager,
                      DBLogManager logManager,
                      Vector<Step> vSteps){

        this.bufManager = bufManager;
        this.logManager = logManager;
        this.vSteps = vSteps;
    }

    @Override
    public void run() {
        for (int i=0; i<vSteps.size(); i++){
            Step step = vSteps.get(i);
            DBResult result = step.execute();

            if ( step instanceof DBRead){
                DBRead read = (DBRead) step;
                //bufManager.read();
            }else if ( step instanceof DBWrite){
                DBWrite write = (DBWrite) step;
                //bufManager.write();
                switch (write.getType()){
                    case dbupdate:
                        //logManager.recordUpdate();
                }
            }
            //step.execute()
        }
        logManager.recordCommit(String.valueOf(ID));
    }
}
