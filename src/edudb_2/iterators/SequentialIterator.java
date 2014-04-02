package edudb_2.iterators;

import java.util.ArrayList;
import java.util.ListIterator;

public class SequentialIterator implements ListIterator{

    private final ListIterator<Object> iterator;
    private ArrayList<Object> list;

    public SequentialIterator(ArrayList<Object> lines){
       this.list = lines;
       this.iterator = list.listIterator();
    }


    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Object next() {
        return iterator.next();
    }

    @Override
    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }

    @Override
    public Object previous() {
        return iterator.previous();
    }

    @Override
    public int nextIndex() {
        return iterator.nextIndex();
    }

    @Override
    public int previousIndex() {
        return iterator.previousIndex();
    }

    @Override
    public void remove() {
        iterator.remove();
    }

    @Override
    public void set(Object o) {
        iterator.set(o);
    }

    @Override
    public void add(Object o) {
        iterator.add(o);
    }
}