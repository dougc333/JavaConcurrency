import java.util.*;
import java.util.concurrent.*;
/**
 * Create a separate Callable for each word in the command line
 * then the get() from Future will then block until the future task is completed
 * Each Callable computes the length of one word. Try this with a much bigger
 * file with 1000 threads or 10000 threads. You can try to preserve the order of the returned
 * va;ues by using a List. 
 * @author dc
 *
 */
public class CallableExample {
	private static ExecutorService pool;
	private static String testMe;
  public static class WordLengthCallable
        implements Callable {
    private String word;
    public WordLengthCallable(String word) {
      this.word = word;
    }
    public Integer call() {
      return Integer.valueOf(word.length());
    }
  }

  //test putting a method into 
  public static class Shutdown implements Callable{
	  public Shutdown(String args[]){
	  if(args[0]=="q"){
		  System.out.println("RECEIVED SHUTDOWN SIGNAL");
		  pool.shutdown();
	  }
	  }
	  public Integer call(){
		  return 0;
	  }
  }
  
  public static void main(String args[]) throws Exception {
	String [] arg={"asdfasdfasfd","aaaaaaa","bbbbbbb","cccccc","ddddd"};
	//wait for input
	Shutdown s = new Shutdown(args);
    pool = Executors.newFixedThreadPool(3);
    Set<Future<Integer>> set = new HashSet<Future<Integer>>();
    for (String word: arg) {
      Callable<Integer> callable = new WordLengthCallable(word);
      Future<Integer> future = pool.submit(callable);
      set.add(future);
    }
    pool.submit(s);
    int sum = 0;
    for (Future<Integer> future : set) {
      sum += future.get();
    }
    System.out.printf("The sum of lengths is %s%n", sum);
    System.exit(sum);
    pool.shutdown();
  }
}
