package com.hackerdojo.TestCrawler;
import java.nio.*;

public class TestBuffer {
	private static int index = 0;

    private static String [] strings = {
        "A random string value",
        "The product of an infinite number of monkeys",
        "Hey hey we're the Monkees",
        "Opening act for the Monkees: Jimi Hendrix",
        "'Scuse me while I kiss this fly",  // Sorry Jimi ;-)
        "Help Me!  Help Me!",
    };


	private static void drainBuffer (CharBuffer b){
		while(b.hasRemaining()){
			System.out.print(b.get());
		}
		System.out.println();
	}
	private static boolean fillBuffer(CharBuffer b){
		if(index >= strings.length){
			return (false);
		}
		String string = strings[index++];
		for(int i=0;i<string.length();i++){
			b.put(string.charAt(i));
		}
		return true;
	}
	public static void main(String []args){
		//direct, JVM tries to avoid additional buffer copies, does this work? 
		CharBuffer b = CharBuffer.allocate(100);
		//ok this is useless. How do we do something with IO? 
		while(fillBuffer(b)){
			b.flip();
			drainBuffer(b);
			b.clear();
		}
		
	}
}
