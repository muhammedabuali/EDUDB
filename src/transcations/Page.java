package transcations;

import DBStructure.DBIndex;
import DBStructure.DBTable;
import data_structures.BPlusTree.DBBTree;
import operators.DBResult;

/**
 * Created by mohamed on 5/20/14.
 */
public class Page implements DBResult {
    private PageID id;
    private PageState pageState;
    private LockState lockState;
    private int lastAccessed;
    private int readers;
    private boolean locked;
    private DBIndex tree;
    private String table;

    public Page(String table) {
        this.table = table;
    }

    public Page() {
    }

    public void free() {
        tree = null;
    }

    public void getAccess(){

    }

    public void setLastAccessed() {
        lastAccessed = TimeUtil.getSeconds();
    }

    public void allocate() {
        tree = new DBBTree(table);
        if (tree instanceof DBBTree){
            ( (DBBTree) tree ).readTable();
        }
    }

    public int getlastAccessed() {
        return lastAccessed;
    }

    public PageState getBufferState() {
        return pageState;
    }

    public Page getCopy() {
        Page page = new Page();
        page.id = id;
        // to make dbbuffermanager main work comment line below
        //page.tree = tree.getCopy();
        return this;
    }

    public DBResult getData(){
        return tree.getIterator();
    }

    public PageID getPageId() {
        return id;
    }

    public void write() {
        tree.write();
    }

    public void setPageID(PageID id) {
        this.id = id;
    }

    @Override
    public void print() {
        tree.getIterator().print();
    }

    @Override
    public int numOfParameters() {
        return 0;
    }

    public enum PageState{
        clean,
        dirty
    }

    public enum LockState{
        free,
        read,
        write,
    }
}
