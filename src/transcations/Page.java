package transcations;

/**
 * Created by mohamed on 5/20/14.
 */
public class Page {
    private PageID id;
    private boolean dirty;
    private boolean inMemory;
    private int lastAccessed;

    public void free() {
    }

    public void setLastAccessed() {
        lastAccessed = TimeUtil.getSeconds();
    }

    public void allocate() {
    }

    public int getlastAccessed() {
        return lastAccessed;
    }
}
