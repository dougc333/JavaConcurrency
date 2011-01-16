import java.util.concurrent.*;


public class TestForkJoin {
	
	
	public TestForkJoin(){
		
	}
	
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
		TestForkJoin tfj = new TestForkJoin();
		ForkJoinPool fjPool = new ForkJoinPool();
		int fortyThree = fjPool.invoke(tfj.new Incrementor(42));
		System.out.println(fortyThree);
		
	}
}
