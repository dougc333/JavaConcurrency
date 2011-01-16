package com.hackerdojo.TestParser;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.cyberneko.html.HTMLConfiguration;
import org.cyberneko.html.filters.DefaultFilter;

public class TestNekoParser{
	

  public class HTMLFilter extends DefaultFilter{
	private Set links = new HashSet();
    private URI baseURI;
	private int numA=0;
	
    public HTMLFilter(URI baseURI){
        this.baseURI = baseURI.normalize();
    }

    public Set getLinks(){
        return this.links;
    }

    public void startElement(org.apache.xerces.xni.QName element,
            org.apache.xerces.xni.XMLAttributes attributes,
            org.apache.xerces.xni.Augmentations augs)
     throws org.apache.xerces.xni.XNIException
     {		
		for(int i=0;i<attributes.getLength();i++){
			if(attributes.getLocalName(i).toLowerCase().equals("href")){
				System.out.println("attrs:"+attributes.getValue(i));
				numA++;
			}
		}
		//careful element names are case sensitive
        if ( "A".equals( element.rawname ) )
        {
        	System.out.println("found A!!!");
        	String href = attributes.getValue( "HREF" );
            String hrefLowerCase = attributes.getValue( "href" );
            
            if(href!=null || hrefLowerCase !=null){
            	 System.out.println("href:"+href);
            	 System.out.println("hrefL:"+hrefLowerCase);         	
            }
            links.add(hrefLowerCase);
        }
    }


  }

	public static void main(String []args) throws Exception{
		TestNekoParser tp = new TestNekoParser();
		HTMLConfiguration ht = new HTMLConfiguration();
		URI u = new URI("http://www.stanford.edu");
		URLConnection uConnect = u.toURL().openConnection();
		HTMLFilter hf = tp.new HTMLFilter(u);
		ht.setDocumentHandler(hf);
		ht.parse(new XMLInputSource(null, "http://www.stanford.edu", u.toString(),uConnect.getInputStream(),"UTF-8"));
		Set links = hf.getLinks();
		System.out.println("links size:"+links.size());
	}
}
