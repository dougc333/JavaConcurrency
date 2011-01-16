import java.util.concurrent.*;

//this version is good, when run you will see a count to 2M
public class MainGood {
	public static final int[] count={0};
	//how to design this so you get less counts? 
	//visibility and atomicity
	public static void main(String []args)throws ExecutionException, InterruptedException{
		Runnable r1 = new Runnable() {
			public  synchronized void  run() {
				for(int i=0;i<1000000;i++){
				count[0]++;
				try{
				Thread.sleep(0);
				}catch(InterruptedException e){
					
				}
				System.out.println("r1: "+count[0]);
				}
				
			}
		};
		Runnable r2 = new Runnable(){
			public  synchronized void run(){
				for(int i=0;i<1000000;i++){
					count[0]++;
					try{
						Thread.sleep(0);
						}catch(InterruptedException e){
							
						}
					System.out.println("r2: "+count[0]);
				}
				
			}
		};
		//when to use cached vs. Fixed Thread pool. When you have
		//a situation where like server which requests 1 thread per request
		//you want to limit the number of threads to an upper bound to avoid 
		//slowing the system down. Better to deny requests after a certain point
		//to keep the other requests happy/running fast
		ExecutorService ex = Executors.newCachedThreadPool();
		//question mark means Runnable doesn't return anything
		Future<?> f1 = ex.submit(r1);
		Future<?> f2 = ex.submit(r2);
		f1.get();
		f2.get();
		System.out.println("count:"+count[0]);
		
		
	}
}
