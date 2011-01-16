
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StrLen {

	public static void main(String[] args) {
		
		System.out.println("Enter a string: ");
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(executor);
		try {
			
		 while(true){
			 final String in = stdin.readLine();
			 if (in.equals("quit")){
				 executor.shutdown();
				 break;
			 }
			 Callable<Integer> task = new Callable<Integer>(){
			 
				@Override
				public Integer call() {
					return showLength(in);
				}
			};
			cs.submit(task);
			
		 }
		 
			stdin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int showLength(String str) {
	    try {
	        Thread.sleep(5000);
	    } catch (InterruptedException e) {
	        System.out.println("Interrupted, was calculating length of <" + str + ">");
	        return -1;
	    }
	    System.out.println("Length of <" + str + "> is " + str.length());	    
	    return str.length();

	}
}
