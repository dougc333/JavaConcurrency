import java.util.concurrent.*;

public class Consumer implements Runnable {

 private final BlockingQueue<String> queue;

 public Consumer(BlockingQueue<String> queue) {
  this.queue = queue;
 }

 @Override
 public void run() {
  try {
   while (true) {
    String str = queue.take();
    System.out.println("Consuming '" + str + "'.");
   }
  } catch (InterruptedException e) {
   System.out.println("Consumer interrupted!");
   Thread.currentThread().interrupt();
  }
 }
}