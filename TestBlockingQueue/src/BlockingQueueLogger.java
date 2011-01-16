import java.util.concurrent.*;


//code from: http://www.javamex.com/tutorials/blockingqueue_example.shtml
//it is incomplete. you need 2 threads, one to add and one to remove.

public class BlockingQueueLogger extends Thread{
	public static final BlockingQueueLogger instance = new BlockingQueueLogger();
	private BlockingQueue<String> itemsToLog = new ArrayBlockingQueue<String>(100);
	private static final String SHUTDOWN_REQ="SHUTDOWN";
	private volatile boolean shuttingDown, loggerTerminated;
	
	public static BlockingQueueLogger getLogger(){
		System.out.println("getLogger");
		return instance;
	}
	private BlockingQueueLogger(){
		System.out.println("ctor");
		this.shuttingDown=false;
		this.loggerTerminated=false;
		start();
	}
	
	
	public void log(String str){
		System.out.println("str log:"+str);
		if(shuttingDown || loggerTerminated)
			return;
			try{
				itemsToLog.put(str);
				System.out.println("size:"+itemsToLog.size());
			}catch(InterruptedException ie){
				Thread.currentThread().interrupt();
				throw new RuntimeException("bad");
			}
		
	}
	
	public void shutDown() throws InterruptedException{
		shuttingDown = true;
		itemsToLog.put(SHUTDOWN_REQ);
		
	}
	
	
	public void run(){
		try{
			System.out.println("run");
			String item;
			while((item = itemsToLog.take())!=SHUTDOWN_REQ ){
				System.out.println(item);
			}
		}catch(InterruptedException ie){
			
		}finally{
			loggerTerminated=true;
		}
	}
	public static void main(String []args){
		BlockingQueueLogger blq = BlockingQueueLogger.getLogger();
		//we need another thread to add to the queue
		
		blq.log("test");
		blq.log("test");
		blq.log("test");
		
		blq.run();
		
		
	}

}
