package com.hackerdojo.concurrency;
import java.util.concurrent.*;


public class TestSortClass {

	class SortTask extends RecursiveAction {
		private static final int THRESHOLD=5000;
		private final long[] array; final int lo; final int hi;
		   
		SortTask(long[] array, int lo, int hi) {
		     this.array = array; this.lo = lo; this.hi = hi;
		}
		
		protected void sequentiallySort(long arr[], int lo, int high){
			for(int i = 0;i<arr.length;i++){
				long current = arr[i];
				long next = arr[i+1];
				if(current>next){
					arr[i]=arr[i+1];
					arr[i+1]=current;
				}
				i++;
			}
		}
		//what the hell am I merging into? there should be 2 arrays 
		protected void merge(long arr[], int lo, int hi){
		    
		}	
		
		protected void compute() {
		 if (hi - lo < THRESHOLD)
		    sequentiallySort(array, lo, hi);
		 else {
		    int mid = (lo + hi) >>> 1;
		    invokeAll(new SortTask(array, lo, mid),new SortTask(array, mid, hi));
		       merge(array, lo, hi);
		  }
		}
	}//end SortTask
	public static void main(String []args){
		
	}
		
}

	

