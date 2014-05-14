package DBStructure;

import java.util.ArrayList;

/**
 * Created by mohamed on 4/11/14.
 */
public interface DBIndex {
    public ArrayList<String> getColumns();
    public void write();
    public void insert(int key, DBRecord value);
}
