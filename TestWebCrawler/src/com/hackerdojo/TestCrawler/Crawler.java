package com.hackerdojo.TestCrawler;
import java.util.concurrent.*;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Node;


//doug chang doug.chang@hackerdojo.com
//@net.jcip.annotations.ThreadSafe
public class Crawler implements Runnable {
	private static BlockingQueue<String> urlLinks=null;
	private static BlockingQueue<Node> urlContent=null;
	private static ConcurrentHashMap<String,String> visitedLinks;
	
	private static int numLinks=0;
	
	public Crawler(BlockingQueue<String> links,BlockingQueue<Node> pages, ConcurrentHashMap<String,String> visited){
		this.urlContent = pages;
		this.urlLinks = links;
		this.visitedLinks = visited;
	}
	
	public void crawl() throws Exception{
		String getPage = urlLinks.take();
		System.out.println("CRAWLER getPage:"+getPage);
		DOMParser parser = new DOMParser();
		parser.parse(getPage);
		Node n = parser.getDocument();
		System.out.println("node size:"+n.getNodeName());
		visitedLinks.putIfAbsent(getPage, "");
		urlContent.put(n);
	}
	
	@Override
	public void run() {
		try{
			crawl();
		}catch(Exception e){
			Thread.currentThread().interrupt();
		}
	}

}
