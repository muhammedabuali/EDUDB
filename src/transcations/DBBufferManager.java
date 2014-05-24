package transcations;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohamed on 5/20/14.
 */
public class DBBufferManager {
    HashMap<PageID, Page> used;
    HashMap<PageID, Page> empty;
    ArrayList<Page> emptySlots;
    ArrayList<Page> usedSlots;

    public void init( ){
        emptySlots = new ArrayList<>();
        usedSlots = new ArrayList<>();
    }

    public synchronized Page read( PageID pageID, boolean bModify ){
        if (used.containsKey(pageID)){
            Page page1 = used.get(pageID);
            page1 = page1.getCopy();
            if (bModify){

            }
            page1.setLastAccessed();
            return page1;
        }else{
            Page page1 = empty.get(pageID);
            if (used.size() == DBConfig.getMaximumUsedBufferSlots()){
                removeLRU();
            }
            allocate(page1);
            page1.setLastAccessed();
            return page1;
        }
    }

    private void allocate(Page page) {
    }

    public synchronized void write( PageID pageID, Page page ){
        if (used.containsKey(pageID)){
            Page page1 = used.get(pageID);
            page1.setLastAccessed();
        }else{
            Page page1 = empty.get(pageID);
            if (used.size() == DBConfig.getMaximumUsedBufferSlots()){
                removeLRU();
            }
            page1.allocate();
            page1.setLastAccessed();
        }
    }

    class LRUThreaad implements Runnable{
        @Override
        public void run() {
            removeLRU();
        }
    }

    private void removeLRU() {
        Page toBeReplaced = null;
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i=0; i< usedSlots.size(); i++){
            if(usedSlots.get(i).getlastAccessed() < min){
                toBeReplaced = usedSlots.get(i);
                min = usedSlots.get(i).getlastAccessed();
                minIndex = i;
            }
        }
        if (minIndex != -1){
            usedSlots.remove(minIndex);
            emptySlots.add(toBeReplaced);
            if (toBeReplaced.getBufferState().equals(Page.PageState.dirty)){
                writePage(toBeReplaced);
            }
            toBeReplaced.free();
        }
    }

    private void writePage(Page toBeReplaced) {

    }
}
