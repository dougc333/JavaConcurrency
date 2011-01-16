import java.io.*;
import java.util.*;

public class HelloWorld {
	public static void main(String []args){
		Console c = System.console();
		//String input = c.readLine();
		while(true){
			String input = c.readLine();
			System.out.println("input:"+input);
			if(input.equals("q")){
				System.out.println("quitting");
				break;
			}
		}
		System.out.println("done!!!");
	}
}
