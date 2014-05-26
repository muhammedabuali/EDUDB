package transcations;

import sun.org.mozilla.javascript.Synchronizer;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohamed on 5/20/14.
 */
public class DBBufferManager {
    HashMap<PageID, Page> used;
    HashMap<PageID, Page> empty;
    HashMap<PageID, Page.LockState> locks;
    HashMap<PageID, ArrayList<Thread> > listeners;
    HashMap<PageID, Integer> readersCount;
    ArrayList<Page> emptySlots;
    ArrayList<Page> usedSlots;

    public void init( ){
        used = new HashMap<>();
        empty = new HashMap<>();
        locks = new HashMap<>();
        listeners = new HashMap<>();
        readersCount = new HashMap<>();
        emptySlots = new ArrayList<>();
        usedSlots = new ArrayList<>();
    }

    public synchronized Page read( PageID pageID, boolean bModify ){
        if (used.containsKey(pageID)){
            System.out.println("found" + (bModify? " write": " read"));
            Page page1 = used.get(pageID);
            page1 = page1.getCopy();
            if (bModify){
                if (locks.get(pageID) != Page.LockState.free){
                    listeners.get(pageID).add(Thread.currentThread());
                    return null;
                }
                locks.put(pageID, Page.LockState.write);
                page1.setLastAccessed();
                return page1;
            }
            if (locks.get(pageID) == Page.LockState.write){
                listeners.get(pageID).add(Thread.currentThread());
                readersCount.put(pageID,readersCount.get(pageID) + 1);
                return null;
            }
            locks.put(pageID, Page.LockState.read);
            page1.setLastAccessed();
            readersCount.put(pageID, readersCount.get(pageID) + 1);
            return page1;
        }else{
            System.out.println("not found");
            Page page1 = empty.get(pageID);
            if (used.size() == DBConfig.getMaximumUsedBufferSlots()){
                System.out.println("what ");
                removeLRU();
            }
            allocate(pageID, page1);
            if (bModify){
                locks.put(pageID, Page.LockState.write);
            }else {
                locks.put(pageID, Page.LockState.read);
                readersCount.put(pageID, readersCount.get(pageID)+1);
            }
            page1.setLastAccessed();
            return page1;
        }
    }

    private void allocate(PageID pageId, Page page) {
        used.put(pageId, page);
        locks.put(pageId, Page.LockState.free);
        listeners.put(pageId, new ArrayList<Thread>());
        readersCount.put(pageId, 0);
    }

    public synchronized void write( PageID pageID, Page page ){
        used.put(pageID, page);
        releasePage(pageID);
    }

    public synchronized void releasePage(PageID pageID){
        readersCount.put(pageID, readersCount.get(pageID) -1 );
        if (locks.get(pageID) == Page.LockState.write ||
                (locks.get(pageID) == Page.LockState.read && readersCount.get(pageID) == 0) ){
            System.out.println("release me");
            locks.put(pageID, Page.LockState.free);
            ArrayList<Thread> threads = listeners.get(pageID);
            System.out.println("listeners " + threads.size());
            for (Thread t: threads){
                synchronized (t){
                    System.out.println("wake up");
                    System.out.println(t.getName());
                    t.notify();
                    System.out.println(" coomon");
                }
            }
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

    public static class Reader implements Runnable{

        private final PageID id;
        private final DBBufferManager manager;
        private int count;

        public Reader(PageID id, DBBufferManager manager, int count){

            this.id = id;
            this.manager = manager;
            this.count = count;
        }


        @Override
        public void run() {
            System.out.println("reader" + count);
            manager.read(id, false);
            synchronized (this){
                try {
                    wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            manager.releasePage(id);
            System.out.println("reader" + count + " released");
        }
    }

    public static class Writer implements Runnable{

        private final PageID id;
        private final DBBufferManager manager;
        private int count;

        public Writer(PageID id, DBBufferManager manager, int count){

            this.id = id;
            this.manager = manager;
            this.count = count;
        }

        @Override
        public void run() {
            System.out.println("writer" + count);
            Page read = manager.read(id, true);
            if (read == null){
                try {
                    Thread thread = Thread.currentThread();
                    synchronized (thread){
                        System.out.println("going to sleep ");
                        thread.wait();
                        System.out.println("sleepy ");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            synchronized (this){
                try {
                    wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("awake ");
            manager.releasePage(id);
            System.out.println("writer " + count + " released");
            //manager.write();
        }
    }

    public static void main(String [] args){
        DBBufferManager manager = new DBBufferManager();
        manager.init();
        Page page1 = new Page();
        PageID id1 = new PageID();
        manager.empty.put(id1, page1);
        Writer writer1 = new Writer(id1, manager, 1);
        Thread t3 = new Thread(writer1);
        System.out.println(t3.getName());
        t3.start();
        Reader reader1 = new Reader(id1, manager, 1);
        Thread t1 = new Thread(reader1);
        System.out.println(t1.getName());
        t1.start();
        Reader reader2 = new Reader(id1, manager, 2);
        Thread t2 = new Thread(reader2);
        System.out.println(t2.getName());
        t2.start();
    }
}
