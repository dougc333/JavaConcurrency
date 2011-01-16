import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
* This is a simple example class which tests the Producer/Consumer example
* using ArrayBlockingQueue.
* 
* @author swiki
* 
*/
public class TestArrayBlockingQueue {
public static void main(String args[]) {
// testSlowProducer();
testSlowConsumer();
}

/**
* This test uses 2 consumers and 1 producer which will make consumers
* faster then producer.
* 
* Only for demonstration purpose 1. You can also try
* Consumer.setTimeToConsume() method to explicitly slow down/speed up the
* consumer.
* 
* 2. You can also try Producer.setTimeToProduce() method to explicitly slow
* down/speed up the producer.
* 
*/
public static void testSlowProducer() {
BlockingQueue q = new ArrayBlockingQueue<String>(100);
Producer p = new Producer(q);
Consumer c1 = new Consumer(q);
Consumer c2 = new Consumer(q);
new Thread(p).start();
new Thread(c1).start();
new Thread(c2).start();
}

/**
* This test uses 2 producers and 1 consumer which will make consumers
* slower then producer.
* 
* Only for demonstration purpose 1. You can also try
* Consumer.setTimeToConsume() method to explicitly slow down/speed up the
* consumer.
* 
* 2. You can also try Producer.setTimeToProduce() method to explicitly slow
* down/speed up the producer.
* 
*/
public static void testSlowConsumer() {
BlockingQueue q = new ArrayBlockingQueue<String>(100);
Producer p = new Producer(q);
Producer p2 = new Producer(q);
Consumer c1 = new Consumer(q);
new Thread(p).start();
new Thread(p2).start();
new Thread(c1).start();
}

}

