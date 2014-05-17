package operators;

import DBStructure.DBRecord;

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
        //if(table1 instanceof)
        /*DBBTreeIterator itr = this;
        do {
            BTreeNode element = (BTreeNode) itr.cur;
            element.filter(conditions);
            out+= (element.project(columns));
        }while (itr.next() != null);*/
        return null;
    }

    public void finish() {
        finished = true;
    }
}
