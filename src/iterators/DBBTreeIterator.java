package iterators;

import data_structures.BPlusTree.DBBTree;

import java.util.ListIterator;

/**
 * Created by mohamed on 5/12/14.
 */
public class DBBTreeIterator implements ListIterator {
    DBBTree tree;
    public DBBTreeIterator(DBBTree tree){
        this.tree = tree;
        init();
    }

    private void init() {

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

    }
}
