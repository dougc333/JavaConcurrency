package com.hackerdojo.concurrency;
import java.util.concurrent.*;

public class SumArrayRecursiveAction extends RecursiveAction{
	private int input[];
	private int finalOutput[];
	private int start;
	private int end;
	private static int SEQUENTIAL_THRESHOLD = 5000;
		
	public SumArrayRecursiveAction(int inputArr[], int start, int end, int output[]){
		this.input = inputArr;
		this.start = start;
		this.end = end;
		this.finalOutput = output;
	}
	public void computeDirectly(){
		int sum=0;
		for(int i=start;i<end;i++){
			sum = input[i]+sum;
		}
		int i=0;	
		while(this.finalOutput[i]!=0){
			i++;
		}
		this.finalOutput[i]=sum;
	}
	public void compute(){
		if((end-start)<SEQUENTIAL_THRESHOLD){
			computeDirectly();
			return;
		}else{
			int split = (start+end)/2;			
			System.out.println("start:"+start+" end:"+end+" split:"+split);
			SumArrayRecursiveAction s1 = new SumArrayRecursiveAction(input,start,split,this.finalOutput);
			SumArrayRecursiveAction s2 = new SumArrayRecursiveAction(input,split,end,this.finalOutput);
			invokeAll(s1,s2);
		}
	}
}
