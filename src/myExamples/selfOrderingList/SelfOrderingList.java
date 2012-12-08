package myExamples.selfOrderingList;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eatmuchpie
 * Date: 08/12/2012
 * Time: 18:28
 *
 * A self-ordering list of Integers.  The sorting is deferred to a
 * background thread on adding a new Integer, but any attempt to read
 * from the list while being sorted results in hanging until the list
 * is sorted.
 */
public interface SelfOrderingList {
    /**
     * Adds the integer to the list. Sorting is deferred to a background thread.
     *
     * @param i The integer to add.
     */
    void add(int i);

    /**
     * Adds all integers in the given list, with deferred sorting.
     *
     * @param list The list of integers to add to the list.
     */
    void addAll(List<Integer> list);

    /**
     * Gets the i'th element in the list.  If the list is still being sorted,
     * this will hang until that's done.
     *
     * @param i
     * @return
     */
    int get(int i);

    /**
     * Returns true if the list is sorted and ready to be read, or false if not.
     * @return Whether the list is sorted.
     */
    boolean isSorted();
}
