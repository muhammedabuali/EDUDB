package data_structures.BPlusTree;

import operators.DBResult;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Created by mohamed on 5/12/14.
 */
public class DBBTreeIterator implements ListIterator, DBResult {
    BTree tree;
    BTreeLeafNode cur;
    private int index;

    public DBBTreeIterator(BTree tree){
        this.tree = tree;
        this.cur = tree.getSmallest();
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        if(cur == null)
            return false;
        return cur.rightSibling != null;
    }

    @Override
    public Object next() {
        cur = (BTreeLeafNode) cur.rightSibling;
        return cur;
    }

    @Override
    public boolean hasPrevious() {
        return cur.leftSibling != null;
    }

    @Override
    public Object previous() {
        return cur.leftSibling;
    }

    @Override
    public int nextIndex() {
        return index++;
    }

    @Override
    public int previousIndex() {
        return index--;
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

    @Override
    public void print() {
        DBBTreeIterator itr = this;
        System.out.print(itr.cur + " ");
        while (itr.hasNext()){
            BTreeNode element = (BTreeNode) itr.next();
            System.out.print(element + " ");
        }
    }
}
