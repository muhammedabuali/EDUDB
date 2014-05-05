package DBStructure;

/**
 * Created by mohamed on 4/19/14.
 */
public class DBColumn {
    int order;
    String tableName;
    public DBColumn(int num, String tableName) {
        this.order = num;
        this.tableName = tableName;
    }
}
