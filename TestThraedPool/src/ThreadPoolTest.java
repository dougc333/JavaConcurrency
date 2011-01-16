import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// TPT gives more control than executor service 
// read source code.
// lots of variations. for work you will have to create your own. eventually.
// Add UI. pause and resume for fun.
public class ThreadPoolTest {
    private static ThreadPoolExecutor tp;
    final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(
	    5);
    private static final int poolSize = 2;
    private static final int maxPoolSize = 2;
    private static final long keepAliveTime = 10;

    public ThreadPoolTest() {
	tp = new ThreadPoolExecutor(poolSize, maxPoolSize, keepAliveTime,
		TimeUnit.SECONDS, queue);
    }

    public void runTask(Runnable r) {
	tp.execute(r);
    }

    public void shutDown() {
	tp.shutdown();
    }

    public static void main(String args[]) {
	ThreadPoolTest tpt = new ThreadPoolTest();
	tpt.runTask(new Runnable() {
	    @Override
	    public void run() {
		for (int i = 0; i < 10; i++) {
		    try {
			System.out.println("First Task");
			Thread.sleep(1000);
		    } catch (InterruptedException ie) {
		    }
		}

	    }

	});

	tpt.runTask(new Runnable() {
	    @Override
	    public void run() {
		for (int i = 0; i < 10; i++) {
		    try {
			System.out.println("Second Task");
			Thread.sleep(1000);
		    } catch (InterruptedException ie) {
		    }
		}

	    }

	});
    }
}
