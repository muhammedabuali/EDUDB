package operators;

import DBStructure.DBRecord;

public interface DBCond extends DBParameter{

    boolean evaluate(DBRecord dbRecord);
}
