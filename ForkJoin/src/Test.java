import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;



public class Test {
		
	class Incrementor extends RecursiveTask<Integer> {
	    int theNumber;
	    public Incrementor(int x) {
		     theNumber = x;
	    }		   
		public Integer compute() {
			return theNumber + 1;
		}
	}
	
	public static void main(String []args){
		System.out.println("asdfasdfsadf");
		Test t = new Test();
		ForkJoinPool fjPool = new ForkJoinPool();
		//int fortyThree = fjPool.invoke(t.new Incrementor(42));
		//System.out.println(fortyThree);
	}
}
