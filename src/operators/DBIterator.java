package operators;

/**
 * Created by mohamed on 5/18/14.
 */
public interface DBIterator extends DBResult{
    public void project(SelectColumns columns);
}
