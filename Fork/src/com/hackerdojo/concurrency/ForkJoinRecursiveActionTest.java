package com.hackerdojo.concurrency;
import java.util.concurrent.ForkJoinPool;


public class ForkJoinRecursiveActionTest {
	private static final int NUM=1000000;
	public static void main(String []args){
		int test[] = new int[NUM];
		//we should not need this array, need a better data structure
		int result[] = new int[NUM/5000+56];
		
		for(int i=0;i<result.length;i++){
			result[i]=0;
		}
		for(int i=0;i<test.length;i++){
			test[i]=i;
		}
		int sum=0;
		for(int i=0;i<test.length;i++){
			sum=sum+(test[i])^2;
		}
		System.out.println("sum is:"+sum);
	
		ForkJoinPool f = new ForkJoinPool();
		SumArrayRecursiveAction s = new SumArrayRecursiveAction(test,0,test.length,result);
		f.invoke(s);
		int r=0;
		for(int i=0;i<result.length;i++){
			r = result[i]+r;
		}
		System.out.println(" fork join sum:"+r);
		
	}
}
