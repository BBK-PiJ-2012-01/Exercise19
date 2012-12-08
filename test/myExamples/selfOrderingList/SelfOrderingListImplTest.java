package myExamples.selfOrderingList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: eatmuchpie
 * Date: 08/12/2012
 * Time: 19:44
 * To change this template use File | Settings | File Templates.
 */
public class SelfOrderingListImplTest {
    private SelfOrderingList lst;
    private static List<Integer> disordered = new ArrayList<Integer>();
    private static List<Integer> ordered = new ArrayList<Integer>();

    @BeforeClass
    public static void staticSetUp() {
        int random_value;
        for (int i = 0; i < 1000; ++i) {
            random_value = Double.valueOf(Math.abs(Math.random() * 1000)).intValue();
            disordered.add(random_value);
            ordered.add(random_value);
        }

        Collections.sort(ordered);
    }

    @Before
    public void setUp() throws Exception {
        lst = new SelfOrderingListImpl();
    }

    @Test
    public void testAddThenGet() {
        lst.add(1);
        assertEquals(1, lst.get(0));
        lst.add(3);
        lst.add(2);
        lst.add(4);

        assertEquals(2, lst.get(1));
        assertEquals(3, lst.get(2));
        assertEquals(4, lst.get(3));
    }

    @Test
    public void testAdd() throws Exception {
        for (int i = 0; i < 1000; ++i) {
            lst.add(disordered.get(i));
        }

        checkOrdered();
    }

    private void checkOrdered() {
        for (int i = 0; i < 1000; ++i) {
            assertEquals((int) ordered.get(i), lst.get(i));
        }
    }

    @Test
    public void testAddAll() throws Exception {
        lst.addAll(disordered);
        checkOrdered();
    }

    @Test
    public void testGet() throws Exception {
        lst.add(5);
        assertEquals(5, lst.get(0));

        lst.add(3);
        assertEquals(3, lst.get(0));
        assertEquals(5, lst.get(1));

        lst.add(4);
        assertEquals(3, lst.get(0));
        assertEquals(4, lst.get(1));
        assertEquals(5, lst.get(2));
    }

    @Test
    public void testIsSorted() throws Exception {
        lst.addAll(disordered);
        while (!lst.isSorted()) {
            System.out.println("Still sorting...");
        }
    }
}
