package com.hackerdojo.TestCrawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.w3c.dom.Node;

//doug chang doug.chang@hackerdojo.com
public class Crawl {

    public static void main(String[] args) throws ExecutionException,
	    InterruptedException {
	new Crawl();
	BlockingQueue<String> links = new LinkedBlockingQueue<String>();
	BlockingQueue<Node> downloadedWebPages = new LinkedBlockingQueue<Node>();
	// there should be a limit to the hashmap or we will run out of memory.
	ConcurrentHashMap<String, String> visitedURLS = new ConcurrentHashMap<String, String>();

	ExecutorService threadPool = Executors.newCachedThreadPool();
	links.add("http://www.stanford.edu");
	Future<?> f1 = null;
	Future<?> f2 = null;
	Future<?> f3 = null;
	Future<?> f4 = null;
	Future<?> f5 = null;
	Future<?> f6 = null;
	Future<?> f7 = null;
	Future<?> f8 = null;
	Future<?> f9 = null;
	Future<?> f10 = null;
	Future<?> f11 = null;
	Future<?> f12 = null;

	while (!threadPool.isShutdown()) {
	    if (!links.isEmpty()) {
		System.out.println("CRAWL visitedURLS size:"
			+ visitedURLS.size());
		f1 = threadPool.submit(new Crawler(links, downloadedWebPages,
			visitedURLS));
		f1.get();// put his into an exception block?
		if (links.size() > 100) {
		    f3 = threadPool.submit(new Crawler(links,
			    downloadedWebPages, visitedURLS));
		    f4 = threadPool.submit(new Crawler(links,
			    downloadedWebPages, visitedURLS));
		    f5 = threadPool.submit(new Crawler(links,
			    downloadedWebPages, visitedURLS));
		    // f6 = threadPool.submit(new
		    // Crawler(links,downloadedWebPages,visitedURLS));
		    // f7 = threadPool.submit(new
		    // Crawler(links,downloadedWebPages,visitedURLS));
		    // f8 = threadPool.submit(new
		    // Crawler(links,downloadedWebPages,visitedURLS));
		    // f9 = threadPool.submit(new
		    // Crawler(links,downloadedWebPages,visitedURLS));
		    // f10 = threadPool.submit(new
		    // Crawler(links,downloadedWebPages,visitedURLS));
		    // f11 = threadPool.submit(new
		    // Crawler(links,downloadedWebPages,visitedURLS));
		    // f12 = threadPool.submit(new
		    // Crawler(links,downloadedWebPages,visitedURLS));

		    // what if we dont call get()? it doesnt block.
		    f3.get();
		    f4.get();
		    f5.get();
		    // f6.get();
		    // f7.get();
		    // f8.get();
		    // f9.get();
		    // f10.get();
		    // f11.get();
		    // f12.get();

		}
		if (!downloadedWebPages.isEmpty()) {
		    f2 = threadPool.submit(new Indexer(links,
			    downloadedWebPages, visitedURLS));
		    f2.get();
		}
	    }
	}
    }
}
