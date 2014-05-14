package operators;

public interface DBMulCondition extends DBCond{
    public void giveParameter(DBCond param);
    public DBCond[] getChildren();
}
