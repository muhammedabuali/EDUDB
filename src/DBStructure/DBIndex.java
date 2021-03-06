package DBStructure;

import operators.DBIterator;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/11/14.
 */
public interface DBIndex {
    public ArrayList<DBColumn> getColumns();
    public void write();
    public void insert(int key, DBRecord value);
    public DBIterator getIterator();
    public DBIndex getCopy();
}
