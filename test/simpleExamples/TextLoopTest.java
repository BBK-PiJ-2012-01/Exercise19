package simpleExamples;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: eatmuchpie
 * Date: 07/12/2012
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class TextLoopTest {

    /**
     * Ran without threads, loops and iterations run sequentially
     * (so loop 1 iteration 1, then loop 1 iteration 2, etc...)
     * @throws Exception
     */
    @Test
    public void testMainWithoutThreads() throws Exception {
        TextLoop.main(new String[]{"0"});
    }

    /**
     * Ran with threads, each loop is ran as a separate thread,
     * so loops appear on screen randomly.
     * @throws Exception
     */
    @Test
    public void testMainWithThreads() throws Exception {
        TextLoop.main(new String[]{"1"});
    }
}
