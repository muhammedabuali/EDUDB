package transcations;

/**
 * Created by mohamed on 5/21/14.
 */
public interface DBWrite {
    DBWriteType getType();

    public enum DBWriteType{
        dbinsert,
        dbupdate,
        dbdelte
    }
}
