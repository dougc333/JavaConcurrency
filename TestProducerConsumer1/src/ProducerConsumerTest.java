import java.util.concurrent.*;


public class ProducerConsumerTest {
 private final ExecutorService pool = Executors.newFixedThreadPool(10);
 private final BlockingQueue queue = new LinkedBlockingQueue();
 
// @Before
 public void setUp() {

 }

 //@After
 public void tearDown() {
  pool.shutdown(); // Disable new tasks from being submitted
  try {
   System.out.println("Wait 30s for existing tasks to terminate...");
   if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
    System.out.println("Cancel currently executing tasks");
    pool.shutdownNow();  
    // Wait a while for tasks to respond to being cancelled
    if (!pool.awaitTermination(60, TimeUnit.SECONDS))
     System.err.println("Pool did not terminate");
   }
  } catch (InterruptedException ie) {
   // (Re-)Cancel if current thread also interrupted
   pool.shutdownNow();
   // Preserve interrupt status
   Thread.currentThread().interrupt();
  }
 }

 //@Test
 public void test() {
  try {
// pool.submit(); 
   pool.execute(new Producer("/home/dc/", queue));
   pool.execute(new Consumer(queue));
  } catch (Exception e) {
   e.printStackTrace();
   pool.shutdown();
  }
 }
 public static void main(String []args){
	 ProducerConsumerTest pct = new ProducerConsumerTest();
	 pct.test();
	 pct.tearDown();
 }
}