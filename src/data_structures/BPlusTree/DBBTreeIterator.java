package data_structures.BPlusTree;

import operators.DBResult;
import operators.SelectColumns;

import java.util.ListIterator;

/**
 * Created by mohamed on 5/12/14.
 */
public class DBBTreeIterator implements ListIterator, DBResult {
    BTree tree;
    BTreeLeafNode cur;
    private int index;
    SelectColumns columns;

    public DBBTreeIterator(BTree tree){
        this.tree = tree;
        this.cur = tree.getSmallest();
        this.index = 0;
    }

    public void project(SelectColumns columns){
        this.columns = columns;
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
    public String toString(){
        String out = "";
        DBBTreeIterator itr = this;
        do {
            BTreeNode element = (BTreeNode) itr.cur;
            out+= (element.project(columns) );
        }while (itr.next() != null);
        return out;
    }

    @Override
    public void print() {
        System.out.print(this);
    }
}
