import java.util.concurrent.*;

public class TestCompletionService {
	private static final int count[]={0};
	
	public static void main(String []args){
		
		
		Callable<Integer> c1 = new Callable<Integer>() {
			public  Integer  call() throws InterruptedException{
				for(int i=0;i<1000000;i++){
				if(i%2==0){
				count[0]++;
				System.out.println("Thread1 i: "+i+" count:"+count[0]);
				Thread.sleep(10);
				}
				}
				return new Integer(count[0]);
				
			}
		};
		Callable<Integer> c2 = new Callable<Integer>() {
			public  Integer  call()  throws InterruptedException {
				for(int i=0;i<100;i++){
				if(i%2==1){
				count[0]++;
				System.out.println("Thread2 i: "+i+" count:"+count[0]);
				Thread.sleep(1000);
				}
				}
				return new Integer(count[0]);
				
			}
		};
		
		//we should see each count complete and print completed
		
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executor);
		completionService.submit(c1);
		completionService.submit(c2);
		//how do you poll or wait for the tasks to finish?
		try{
			Future<Integer> f1 = completionService.take();
			Integer i1 =f1.get(); //what are you getting, first avail? 
			System.out.println("i1:"+i1);
			Future<Integer> f2 = completionService.take();
			Integer i2 = f2.get(); //what are you getting, first avail? 
			System.out.println("i2:"+i2);
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}catch(ExecutionException e){
			System.out.println(e.getCause());
		}
		//bug in the code downloaded from internet, no shutdown
		//marshall put a shutdown in the exception handler
		executor.shutdown();
	}
}
