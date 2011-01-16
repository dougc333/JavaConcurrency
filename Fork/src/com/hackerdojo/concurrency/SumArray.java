package com.hackerdojo.concurrency;
import java.util.concurrent.*;


class SumArray {
      public static void main(String []args){
	   int testarray[] = new int[1000000];
	   for(int i=0;i<testarray.length;i++){
		   testarray[i]=i;
	   }
	   SumArray sa = new SumArray();
	   SumArrayRecursive sr = new SumArrayRecursive(testarray,0,1000000);
	   System.out.println(sr.compute());
   }
   
   
} //endSumArray

	

