package com.hackerdojo.concurrency;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

//import SumArray.SumArrayRecursive;

class SumArrayRecursive extends RecursiveTask<Long> {
	   int low;
	   int high;
	   int[] array;
	   static final ForkJoinPool fjPool = new ForkJoinPool();
	   static final int SEQUENTIAL_THRESHOLD = 5000;

	      
	   public SumArrayRecursive(int[] arr, int lo, int hi) {
		   array = arr;
		   low = lo;
		   high = hi;
	   }
	   long sumArray(int[] array) {
		   return fjPool.invoke(new SumArrayRecursive(array,0,array.length));
	   }
	   protected Long compute() {
		   if(high - low <= SEQUENTIAL_THRESHOLD) {
			   long sum = 0;
			   for(int i=low; i < high; ++i)
				   sum += array[i];
			   return sum;
		   } else {
			   int mid = low + (high - low) / 2;
			   SumArrayRecursive left  = new SumArrayRecursive(array, low, mid);
			   SumArrayRecursive right = new SumArrayRecursive(array, mid, high);
			   left.fork();
			   long rightAns = right.compute();
			   long leftAns  = left.join();
			   return leftAns + rightAns;
		   }
	   }
	   

   	}//end SumArrayRecursive
