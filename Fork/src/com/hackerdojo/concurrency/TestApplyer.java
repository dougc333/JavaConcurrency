package com.hackerdojo.concurrency;
import java.util.concurrent.*;



public class TestApplyer {
	
	double sumOfSquares(ForkJoinPool pool, double[] array) {
		   int n = array.length;
		   Applyer a = new Applyer(array, 0, n, null);
		   pool.invoke(a);
		   return a.result;
		 }
	public static void main(String []args){
		double arr[] = new double[1000000];
		for (int i=0;i<arr.length;i++){
			arr[i]=(double)10;
		}
//		System.gc();
		
		ForkJoinPool fp = new ForkJoinPool();
		TestApplyer ta = new TestApplyer();
		long start = System.nanoTime();
		System.out.println("ForkJoin sum:"+ta.sumOfSquares(fp,arr));
		long end = System.nanoTime();
		System.out.println("Fork Join elapsed time:"+(end-start));
	//	System.gc();
		//time for nonfork
		long start2 = System.nanoTime();
		double slowsum = 0;
		for (int i=0;i<arr.length;i++){
			slowsum =  slowsum + (arr[i]*arr[i]);
		}
		long end2 = System.nanoTime();
		System.out.println("slowsum:"+slowsum+" elapsed time non fork join :"+(end2-start2));
		
	}
	class Applyer extends RecursiveAction {
		private static final long serialVersionUID = 1L;
		final double[] array;
		   final int lo, hi;
		   double result;
		   Applyer next; // keeps track of right-hand-side tasks
		   Applyer(double[] array, int lo, int hi, Applyer next) {
		     this.array = array; this.lo = lo; this.hi = hi;
		     this.next = next;
		   }

		   double atLeaf(int l, int h) {
		     double sum = 0;
		     for (int i = l; i < h; ++i) // perform leftmost base step
		       sum += array[i] * array[i];
		     return sum;
		   }

		   protected void compute() {
		     int l = lo;
		     int h = hi;
		     Applyer right = null;
		     while (h - l > 1 && getSurplusQueuedTaskCount() <= 3) {
		    	 int mid = (l + h) >>> 1;
		        right = new Applyer(array, mid, h, right);
		        right.fork();
		        h = mid;
		     }
		     double sum = atLeaf(l, h);
		     while (right != null) {
		        if (right.tryUnfork()) // directly calculate if not stolen
		          sum += right.atLeaf(right.lo, right.hi);
		       else {
		          right.join();
		          sum += right.result;
		        }
		        right = right.next;
		      }
		     result = sum;
		   }
		 }
	
	
}
