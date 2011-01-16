import java.util.*;
import java.io.*;

public class Node implements Serializable{
	private Vector<Node> children;
	private Vector<File> files;
	private File f;
	
	Node(){
		children= new Vector<Node>();
		files = new Vector<File>();
	}
	
	public void setFile(File f){
		this.f=f;
	}
	public File getFile(){
		return f;
	}
	public Vector<File> getFiles(){
		return files;
	}
	public Vector<Node> getChildren(){
		return children;
	}
	
	public void addChild(Node child){
		children.add(child);
	}
	public void addFile(File f){
		files.add(f);
	}
	public int numChild(){
		return children.size();
	}
	public int numFiles(){
		return files.size();
	}
}
