package operators;

import DBStructure.DBRecord;

import java.util.ArrayList;

/**
 * Created by mohamed on 5/12/14.
 */
public class SelectResult implements DBResult{
    public ArrayList<DBRecord> data;

    @Override
    public void print() {
    }
}
