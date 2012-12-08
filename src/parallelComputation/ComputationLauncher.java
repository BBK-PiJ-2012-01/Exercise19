package parallelComputation;

/**
 * This class launched two heavy computations
 * sequentially first, then in parallel. 
 * Assuming there is more than one processor in 
 * the machine, parallel computations finish
 * earlier.
 */
public class ComputationLauncher {   
    /**
     * How many numbers to process? If too low, there is no noticeable
     * difference.
     */
     public static final int COUNT = 40000000;

    /*
     * The computations to be performed. Stored as fields so 
     * both methods (sequential and parallel) act on exactly 
     * the same data
     */
    private Computation c1 = null;
    private Computation c2 = null;

    /**
     * The main method that launches the computations
     *
     * @param args command-line arguments, ignored
     */
    public static void main(String args[]) {
	  ComputationLauncher c = new ComputationLauncher();
	  c.launch();
    }
   
    private double[] createArray(int size) {
	  double[] result = new double[size];
	  for (int i = 0; i < result.length; i++) 
		{
		    result[i] = Math.random();
		}
	  return result;
    }
   
    private void launch() {
	  // Uncomment the following line to know how many processors your machine has
	  // System.out.println("#CPU: " + Runtime.getRuntime().availableProcessors());
	  long start, stop;
	  c1 = new Computation(createArray(COUNT/2));
	  c2 = new Computation(createArray(COUNT/2));	
	  start = System.currentTimeMillis();
	  sequentialComputations();
	  stop = System.currentTimeMillis();
	  System.out.println("Time without threads: " + (stop - start) + "ms");
	  start = System.currentTimeMillis();
	  parallelComputations();
	  stop = System.currentTimeMillis();
	  System.out.println("Time with threads: " + (stop - start) + "ms");

        start = System.currentTimeMillis();
        parallelComputations(3);
        stop = System.currentTimeMillis();
        System.out.format("Time with 3 threads: %dms", (stop - start));

        int max_threads = Runtime.getRuntime().availableProcessors();
        start = System.currentTimeMillis();
        parallelComputations(max_threads);
        stop = System.currentTimeMillis();
        System.out.format("Time with %d threads: %dms", max_threads, (stop - start));
    }
   
    private void sequentialComputations() {
	  c1.run();
	  c2.run();
	  double result1 = c1.getResult();
	  double result2 = c2.getResult();
	  System.out.println("Result: " + (result1 + result2));
    }

    private void parallelComputations(int max_threads) {
        Computation[] c = new Computation[max_threads];

        // Start the computations
        for (int i = 0; i < max_threads; ++i) {
            c[i] = new Computation(createArray(COUNT / max_threads));
            Thread t = new Thread(c[i]);
            t.start();
        }

        // Get the results from all computations
        double result = 0;
        for (int i = 0; i < max_threads; ++i) {
            result += c[i].getResult();
        }
        System.out.println("Result: " + result);
    }

    private void parallelComputations() {
	  Thread t1 = new Thread(c1);
	  t1.start();
	  Thread t2 = new Thread(c2);
	  t2.start();
	  double result1 = c1.getResult();
	  double result2 = c2.getResult();
	  System.out.println("Result: " + (result1 + result2));
    }
}
