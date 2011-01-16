import java.io.Console;
import java.util.concurrent.*;

@net.jcip.annotations.ThreadSafe
public class StringLengthRev2 {
	public class ShowLength implements Runnable{
		private final String str;
		
		public ShowLength(String s){
			this.str = s;
		}
		
		public void run(){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("Interrupted, was calculating length of <" + str + ">");
				Thread.currentThread().interrupt();
				return;
			}
			System.out.println("Length of <" + str + "> is " + str.length());
		}	
	}
	
	public static void main(String []args) throws InterruptedException{
		StringLengthRev2 strRev2 = new StringLengthRev2();
		BlockingQueue<String> bq = new LinkedBlockingQueue<String>();
		ExecutorService ex = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		Console c = System.console();
		System.out.println("Please type in a String:");
		while(true){
			String input = c.readLine();
			System.out.println("input:"+input);
			System.out.println("Please type in another String:");
			if(input.equals("q")){
				System.out.println("we see Q!!!!!");
				//System.exit(1); immediate shutdown
				//ex.shutdown(); //you can see everything finish. very cool
				ex.shutdownNow(); //tests the interrupt
				break;
			}
			bq.add(input);
			ex.execute(strRev2.new ShowLength(bq.take()));
		}
	}
}
