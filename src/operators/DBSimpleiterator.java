package operators;

import DBStructure.DBRecord;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by mohamed on 5/17/14.
 */
public class DBSimpleiterator implements DBResult, ListIterator, DBIterator{

    private ArrayList<DBRecord> records;
    private SelectColumns columns;
    private ArrayList<DBCond> conditions;

    public DBSimpleiterator(){
        this.records = new ArrayList<DBRecord>();
    }

    @Override
    public String toString(){
        String out = "";
        for (int i=0; i< records.size(); i++){
            if( ! records.get(i).evaluate(conditions).equals("") ) {
                out += records.get(i).project(columns) + "\n";
            }
        }
        return out;
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public int numOfParameters() {
        return 0;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Object previous() {
        return null;
    }

    @Override
    public int nextIndex() {
        return 0;
    }

    @Override
    public int previousIndex() {
        return 0;
    }

    @Override
    public void remove() {

    }

    @Override
    public void set(Object o) {

    }

    @Override
    public void add(Object o) {
        records.add((DBRecord) o);
    }

    @Override
    public void project(SelectColumns columns) {
        if (this.columns == null){
            this.columns = columns;
        }else {
            this.columns.union(columns);
        }
    }

    public void filter(ArrayList<DBCond> conditions) {
        this.conditions = conditions;
    }

    public void filter(DBCond condition) {

    }

    @Override
    public Object first() {
        return null;
    }
}
