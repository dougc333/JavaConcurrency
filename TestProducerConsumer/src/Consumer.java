
 import java.util.concurrent.BlockingQueue;  
   
 /** 
 * This is a simple Consumer example class which uses ArrayBlockingQueue. 
 *  
 * @author swiki 
 *  
 */  
 public class Consumer implements Runnable {  
 private final BlockingQueue queue;  
 private volatile boolean stopConsuming = false;  
 private int timeToConsume = 1;  
   
 Consumer(BlockingQueue q) {  
 queue = q;  
 }  
   
 public void run() {  
 try {  
 while (true) {  
 Object objectFromQueue = queue.poll();  
 /** 
 * The non-blocking poll() method returns null if the queue is 
 * empty 
 */  
 if (objectFromQueue == null) {  
 long start = System.currentTimeMillis();  
 /** 
 * Now use the blocking take() method which can wait for the 
 * object to be available in queue. 
 */  
 objectFromQueue = queue.take();  
 System.out  
 .println("It seems Producer is slow. Consumer waited for "  
 + (System.currentTimeMillis() - start)  
 + "ms");  
 }  
 if (objectFromQueue != null) {  
 consume(objectFromQueue);  
 }  
 if (stopConsuming) {  
 break;  
 }  
 }  
 } catch (InterruptedException ex) {  
 }  
 }  
   
 void consume(Object x) {  
 try {  
 Thread.sleep(timeToConsume);  
 } catch (Throwable t) {  
   
 }  
 }  
   
 public void setStopConsuming(boolean stopConsuming) {  
 this.stopConsuming = stopConsuming;  
 }  
   
 public void setTimeToConsume(int timeToConsume) {  
 this.timeToConsume = timeToConsume;  
 }  
   
 } 