package simpleExamples;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: eatmuchpie
 * Date: 07/12/2012
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */
public class IncreaserTest {

    /**
     * By making the Counter methods synchronized, it isn't interrupted by
     * competing threads when updating and reading the Counter value.
     */
    @Test
    public void testMain() {
        Increaser.main(null);
    }
}
