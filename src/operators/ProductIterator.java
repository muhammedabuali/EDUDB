package operators;

import DBStructure.DBRecord;
import data_structures.BPlusTree.DBBTreeIterator;

import java.util.ArrayList;

/**
 * Created by mohamed on 5/17/14.
 */
public class ProductIterator implements DBResult{

    ArrayList<DBResult> iterators;
    private boolean finished;

    public ProductIterator(){
        iterators = new ArrayList<>();
        finished = false;
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString(){
        return getIterator().toString();
    }

    public void giveIterator(DBResult dbResult) {
        if (dbResult instanceof ProductIterator){
            dbResult = ( (ProductIterator) dbResult ).getIterator();
        }
        iterators.add(dbResult);
    }

    // execute join and return resulting iterator
    private DBResult getIterator() {
        DBSimpleiterator iter = new DBSimpleiterator();
        ArrayList<DBRecord> records = new ArrayList<DBRecord>();
        if(iterators.get(0) instanceof DBBTreeIterator &&
                iterators.get(1) instanceof DBBTreeIterator){
            DBBTreeIterator itr1 = (DBBTreeIterator) iterators.get(0);
            DBBTreeIterator itr2 = (DBBTreeIterator) iterators.get(1);
            DBRecord element = (DBRecord) itr1.first();
            do {
                DBRecord element2 = (DBRecord) itr2.first();
                do{
                    DBRecord record = new DBRecord();
                    record.add(element.getValues(),
                            element.getColumns());
                    record.add(element2.getValues(),
                            element2.getColumns());
                    iter.add(record);
                    element2 = (DBRecord) itr2.next();
                }while (element2 != null);
                element = (DBRecord) itr1.next();
            }while (element != null);
            return iter;
        }
        return null;
    }

    public void finish() {
        finished = true;
    }
}
