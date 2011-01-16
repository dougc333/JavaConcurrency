import java.io.Console;
import java.util.concurrent.*;

@net.jcip.annotations.ThreadSafe
public class StringLength {	
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
		
	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		StringLength strLen = new StringLength();
		Console c = System.console();
				
		ExecutorService ex = Executors.newCachedThreadPool();
		Future<?> f1=null;
		System.out.println("Please type in a string:");
		while(true){
			String input = c.readLine();
			System.out.println("Input: "+input);
			System.out.println("Please type in another string: ");
			if(input.equals("q")){
				//System.exit(1);  //immediate shutdown
				ex.shutdownNow();
				break;
			}
			ShowLength sl =  strLen.new ShowLength(input);
			ex.submit(sl);
			f1 = ex.submit(sl);
		}
		f1.get();
	}

}
