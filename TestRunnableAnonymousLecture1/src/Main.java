import java.util.concurrent.*;

//this version is bad, when run you will not see a count to 2M
public class Main {
	public static final int[] count={0};
	//how to design this so you get less counts? 
	//visibility and atomicity
	public static void main(String []args)throws ExecutionException, InterruptedException{
		Runnable r1 = new Runnable() {
			public  void  run() {
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
			public  void run(){
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
		ExecutorService ex = Executors.newCachedThreadPool();
		Future<?> f1 = ex.submit(r1);
		Future<?> f2 = ex.submit(r2);
		f1.get();
		f2.get();
		System.out.println("count:"+count[0]);
		
		
	}
}
