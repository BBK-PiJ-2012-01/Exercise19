package myExamples.selfOrderingList;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: eatmuchpie
 * Date: 08/12/2012
 * Time: 18:24
 * To change this template use File | Settings | File Templates.
 */
public class SelfOrderingListImpl implements SelfOrderingList {
    private final List<Integer> lst = new ArrayList<Integer>();
    private final LinkedList<Integer> add_cache = new LinkedList<Integer>();
    private boolean sorted = true;
    private final Thread sorter = new Thread(new Runnable() {

        private final LinkedList<Integer> add_queue = new LinkedList<Integer>();

        @Override
        public void run() {
            Iterator<Integer> itr;


            while (true) {
                synchronized (add_cache) {
                    // Move new data from cache to queue
                    add_queue.addAll(add_cache);
                    add_cache.clear();

                    // Wait until there's something to add...
                    while (add_queue.isEmpty()) {
                        setSorted(true);

                        // Before waiting for an add_cache notification, this will let
                        // get(int i) know that it can now read from lst.
                        synchronized (lst) {
                            lst.notifyAll();

                        }

                        try {
                            add_cache.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException("List sorter was interrupted while waiting");
                        }
                        add_queue.addAll(add_cache);
                        add_cache.clear();
                    }
                }

                synchronized (lst) {
                    // Check the special cases - ie. the values to add
                    // should go at the beginning or the end, or the lst
                    // is empty.
                    for (itr = add_queue.iterator(); itr.hasNext();) {
                        Integer val = itr.next();

                        if (lst.isEmpty()) {
                            // If the list is empty, pop from queue into lst
                            lst.add(val);
                            itr.remove();
                        } else if (val <= lst.get(0)) {
                            // If value is less than the first in lst, pop to front.
                            lst.add(0, val);
                            itr.remove();
                        } else if (val > lst.get(lst.size() - 1)) {
                            // If value is greater than the last in lst, pop to end.
                            lst.add(val);
                            itr.remove();
                        }
                    }

                    // Check if the remaining values in add_queue fit in the list
                    // (which they should, since they don't fit at the front or end).

                    for (int i = 1; i < lst.size(); ++i) {

                        Integer next_val = lst.get(i);
                        Integer prev_val = lst.get(i - 1);

                        for (itr = add_queue.iterator(); itr.hasNext(); ) {
                            // For each value in the add_queue
                            Integer val = itr.next();

                            // If it fits into the i'th position in the lst...
                            if (val > prev_val && val <= next_val) {
                                // Pop it into the list
                                lst.add(i, val);
                                itr.remove();

                                // And update next_val to reflect the change
                                next_val = val;
                            }
                        }
                    }
                }
            }

        }
    });

    public SelfOrderingListImpl() {
        sorter.start();
    }

    @Override
    public void add(int i) {
        synchronized(add_cache) {
            add_cache.add(i);
            setSorted(false);
            add_cache.notifyAll();
        }
    }

    @Override
    public void addAll(List<Integer> list) {
        synchronized (add_cache) {
            add_cache.addAll(list);
            setSorted(false);
            add_cache.notifyAll();
        }
    }

    @Override
    public int get(int i) {
        synchronized (lst) {
            while (!isSorted()) {
                try {
                    lst.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted waiting for list to become sorted");
                }
            }
            return lst.get(i);
        }
    }

    private synchronized void setSorted(boolean sorted) {
         this.sorted = sorted;
    }

    @Override
    public synchronized boolean isSorted() {
        return sorted;
    }
}
