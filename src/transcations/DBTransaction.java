package transcations;


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
            if ( vSteps.get(i) instanceof DBRead){
                DBRead read = (DBRead) vSteps.get(i);
                //bufManager.read();
            }else if ( vSteps.get(i) instanceof DBWrite){
                DBWrite write = (DBWrite) vSteps.get(i);
                //bufManager.write();
                switch (write.getType()){
                    case dbupdate:
                        //logManager.recordUpdate();
                }
            }
            //vSteps.get(i).execute()
        }
        logManager.recordCommit(String.valueOf(ID));
    }
}
