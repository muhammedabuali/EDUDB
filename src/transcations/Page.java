package transcations;

import DBStructure.DBIndex;
import DBStructure.DBTable;
import operators.DBResult;

/**
 * Created by mohamed on 5/20/14.
 */
public class Page {
    private PageID id;
    private PageState pageState;
    private LockState lockState;
    private boolean inMemory;
    private int lastAccessed;
    private int readers;
    private boolean locked;
    private DBIndex tree;

    public void free() {
    }

    public void getAccess(){

    }

    public void setLastAccessed() {
        lastAccessed = TimeUtil.getSeconds();
    }

    public void allocate() {
    }

    public int getlastAccessed() {
        return lastAccessed;
    }

    public PageState getBufferState() {
        return pageState;
    }

    public Page getCopy() {
        return null;
    }

    public DBResult getData(){
        return tree.getIterator();
    }

    public enum PageState{
        free,
        clean,
        dirty
    }

    public enum LockState{
        free,
        read,
        write,
    }
}
