
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
	private final File root;
	private final BlockingQueue<String> queue;
	
	public Producer(String path, BlockingQueue<String> queue) {
		this.root = new File(path);
		this.queue = queue;
	}
	
	@Override
	public void run() {
		for(File file : root.listFiles()) {
			if(file.isFile()) {
				readHeadOfFile(file);
			}
		}
		System.out.println("finish producing");
	}
	public void readHeadOfFile(File file) {
		System.out.println("reading file:"+file.getName());
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			queue.add(reader.readLine());
		}catch(IOException e) {
			System.out.println("failed to read file:"+file.getAbsolutePath());
			Thread.currentThread().interrupt();
		}finally {
			closeQuietly(reader);
		}
	}
	public void closeQuietly(Reader reader) {
		if(reader!=null) {
			try {
				reader.close();
			}catch(IOException e) {
				
			}
		}
	}
}
