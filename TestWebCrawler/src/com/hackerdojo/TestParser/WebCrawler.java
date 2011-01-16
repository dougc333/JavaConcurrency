package com.hackerdojo.TestParser;


import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.cyberneko.html.*;
import org.cyberneko.html.parsers.*;
import org.cyberneko.html.filters.*;
import org.w3c.dom.*;
import org.apache.xerces.xni.*;
import java.util.regex.*;
import org.w3c.dom.html.*;
import java.util.*;
import org.apache.xerces.xni.parser.*;
public class WebCrawler {
	private static BlockingQueue<String> crawlQueue;
	private static String webPages;
	private static int numA;
	private static String rootPage;
	
	public WebCrawler(BlockingQueue<String> bq){
		this.crawlQueue = bq;
		numA=0;
	}
	
	//test parsing 
	public void getWebPage(String s) throws Exception{
		URL u = new URL(s);
		URLConnection uConnect = u.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(uConnect.getInputStream()));
		String st = null;
		StringBuffer sb = new StringBuffer();
		while((st=br.readLine())!=null){
			sb.append(st);
		}
		br.close();
		System.out.println(sb.toString());
		this.webPages = sb.toString();
		
	}
	
	public static void  print(Node node, String indent){
			if(node.getNodeName().trim().equals("A")){
			NamedNodeMap nm = node.getAttributes();
			for(int i=0;i<nm.getLength();i++){
				if(nm.item(i).toString().startsWith("href")){
					String hrefLink = nm.item(i).toString().replace("href=", " ").replace("\"", " ").trim();
					if(!hrefLink.startsWith("http")){
						hrefLink=node.getBaseURI()+hrefLink;
						System.out.println(hrefLink);
					}else{
						System.out.println(hrefLink);						
					}
					crawlQueue.add(hrefLink);
				}		
			}
			numA++;
			}		
		Node child = node.getFirstChild();
		while(child!=null){
			print(child,indent+"");
			child = child.getNextSibling();
		}
	}
	//what a pain in the ass. Who the hell had to invent all the 
	//fancy obscure terminology for a simple tree data structure?
	//too much politics so in the end everybody went to JSON if they could
	public class HTMLFilter extends DefaultFilter{
		private Set links = new HashSet();
        private URI baseURI;
		private final Pattern mailURLS=Pattern.compile("mailto:.*");
		
        public HTMLFilter( URI baseURI )
        {
            this.baseURI = baseURI.normalize();
        }

        public Set getLinks()
        {
            return links;
        }

		public void startElement( QName element, XMLAttributes attrs, Augmentations augs )
        {
			System.out.println("calling start element");
			System.out.println(element.toString());
			if(augs!=null){
			System.out.println("aug:"+augs.toString());
			}
			System.out.println("where is:"+element.toString());
			System.out.println("not here:"+attrs.getLocalName(0));
			System.out.println("atrrs"+attrs.getValue(0));
			for(int i=0;i<attrs.getLength();i++){
				//these are the hlinks
				if(attrs.getLocalName(i).toLowerCase().equals("href")){
				System.out.println("attrs:"+attrs.getValue(i));
				numA++;
				}
			}
			
            if ( "A".equals( element.rawname ) )
            {
            	System.out.println("found A!!!");
                //this doesnt work!!!
            	String href = attrs.getValue( "HREF" );
                String hrefL = attrs.getValue( "HREF" );
                
                if(href!=null || hrefL !=null){
                	 System.out.println("href:"+href);
                	 System.out.println("href:"+hrefL);         	
                }
                
                if ( href != null )
                {
                    String link = cleanLink( baseURI, href );
                    System.out.println("link:"+link);
                    links.add( link );
                }
            }
        }

		private String cleanLink( URI baseURI, String link )
        {
			System.out.println("clean link baseURI: "+baseURI.toString()+" link:"+link);
            String ret = link;

            try
            {
                URI linkuri = new URI( ret );
                URI relativeURI = baseURI.relativize( linkuri ).normalize();
                ret = relativeURI.toASCIIString();
                if ( ret.startsWith( baseURI.getPath() ) )
                {
                    ret = ret.substring( baseURI.getPath().length() );
                }

                ret = URLDecoder.decode( ret, "UTF-8" );
            }
            catch ( URISyntaxException e )
            {
            }
            catch ( UnsupportedEncodingException e )
            {
            }

            return ret;
        }
    }

	public static void main(String []args) throws Exception{
		WebCrawler wc = new WebCrawler(new LinkedBlockingQueue());
		//test
		wc.getWebPage("http://www.stanford.edu");
		
		HTMLConfiguration ht = new HTMLConfiguration();
		URI u = new URI("http://www.stanford.edu");
		URL url = new URL("http://www.stanford.edu");
		URLConnection uConnect = url.openConnection();
		HTMLFilter hf = wc.new HTMLFilter(u);
		ht.setDocumentHandler(hf);
		ht.parse(new XMLInputSource(null, "http://www.stanford.edu", u.toString(),uConnect.getInputStream(),"UTF-8"));
		//should get the array list back where? 
		Set links = hf.getLinks();
		System.out.println("links size:"+links.size());
		
/**
		DOMParser parser = new DOMParser();
		parser.parse("http://www.stanford.edu");
		print(parser.getDocument(),"");
    	System.out.println("numA:"+numA);
    	*/
	}	
}
