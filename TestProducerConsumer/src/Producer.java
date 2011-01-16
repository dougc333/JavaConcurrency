import java.util.concurrent.BlockingQueue;

/**
* This is a simple Producer example class which uses ArrayBlockingQueue.
* 
* @author swiki
* 
*/
public class Producer implements Runnable {
private final BlockingQueue queue;
private int timeToProduce = 1;
private volatile boolean stopProducing = false;

Producer(BlockingQueue q) {
queue = q;
}

public void run() {
try {

while (true) {
Object objectForQueue = produce();
if (!queue.offer(objectForQueue)) {
/*
* The non-blocking offer() method returns false if it was
* not possible to add the element to this queue.
*/
long start = System.currentTimeMillis();
/*
* Now use the put method as its a blocking call and it wail
* until the queue space is available.
*/
queue.put(objectForQueue);
System.out
.println("It seems Consumer is slow. Producer waited for "
+ (System.currentTimeMillis() - start)
+ "ms");
}
if (stopProducing) {
break;
}
}
} catch (InterruptedException ex) {
}
}

/**
* Produces something in timeToProduce ms
* 
* @return
*/
public Object produce() {
try {
Thread.sleep(timeToProduce);
} catch (Throwable t) {
}
return "product";
}

public void setTimeToProduce(int timeToProduce) {
this.timeToProduce = timeToProduce;
}
}