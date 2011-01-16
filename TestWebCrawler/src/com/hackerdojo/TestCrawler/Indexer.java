package com.hackerdojo.TestCrawler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

// doug chang doug.chang@hackerdojo.com
public class Indexer implements Runnable {
	private static BlockingQueue<String> urlLinks;
	private static BlockingQueue<Node> urlContent;
	private static ConcurrentHashMap<String,String> links;
	
	public Indexer(BlockingQueue<String> urls, BlockingQueue<Node> pages, ConcurrentHashMap<String, String> visitedLinks){
		this.urlLinks = urls;
		this.urlContent = pages;
		this.links = visitedLinks;
	}
	
	public void index(Node node){
		try{
			if(node.getNodeName().trim().equals("A")){
				NamedNodeMap nm = node.getAttributes();
				for(int i=0;i<nm.getLength();i++){
					if(nm.item(i).toString().startsWith("href")){
						String hrefLink = nm.item(i).toString().replace("href=", " ").replace("\"", " ").trim();
						if(!hrefLink.startsWith("http")){
							hrefLink=node.getBaseURI()+hrefLink;
							System.out.println("indexer href link:"+hrefLink);
						}else{
							System.out.println("indexer href link:"+hrefLink);						
						}

						if(!links.containsKey(hrefLink)){
							System.out.println("INDEXER adding href to queue:"+hrefLink);
							System.out.println("INDEXER num links visited:"+links.size());
							System.out.println("INDEXER urlLinks:"+urlLinks.size());
							System.out.println("INDEXER urlContent:"+urlContent.size());
							
							urlLinks.put(hrefLink);
						}
						
					}		
				}
			}
			Node child = node.getFirstChild();
			while(child!=null){
				index(child);
				child = child.getNextSibling();
			}
			
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try{
		System.out.println("before URLCONTENT take:"+urlContent.size());
		Node node = urlContent.take();
		index(node);
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}
	}

}
