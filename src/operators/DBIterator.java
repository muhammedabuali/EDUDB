package operators;

import transcations.DBRead;

/**
 * Created by mohamed on 5/18/14.
 */
public interface DBIterator extends DBResult{
    public void project(SelectColumns columns);

    public void filter(DBCond condition);

    public Object first();

    public Object next();
}
