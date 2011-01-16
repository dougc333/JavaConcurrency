import java.io.*;
import java.util.*;
import java.text.*;



public class Tree implements Serializable{
	private Node root;
	private int numDirs;
	private int numFiles;
	private String d;
	private Date date;
	
	//private BufferedWriter bw;
	
	Tree(){
		try{	
			date = new Date();
			String DATE_FORMAT = "MM-dd-yyyy HH:mm:ss";
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			d = sdf.format(cal.getTime());

			numDirs=numFiles=0;
		//	bw = new BufferedWriter(new FileWriter("C:\\Users\\dc\\Desktop\\dir.txt"));
		}catch(Exception e){
			e.printStackTrace();
		}
		root = new Node();
	
	}
	
	public Node getRoot(){
		return root;
	}
	
	public  void index(Node n){
		
		//System.out.println("node:"+n.getFile().getAbsolutePath());
		try{
			//bw.write(n.getFile().getAbsolutePath());
			//bw.newLine();
		}catch(Exception e){
			e.printStackTrace();
		}
		numDirs++;
		File [] listFiles = n.getFile().listFiles();
		if(listFiles==null){
			return;
		}
		for(File testMe:listFiles){
			if(testMe.isDirectory() ){
				Node newNode = new Node();
				newNode.addFile(testMe);
				newNode.setFile(testMe);
				
				//System.out.println("processing directory:"+testMe.getAbsolutePath());
				if(!testMe.isHidden()){
					//System.out.println("not hidden adding directory:"+testMe.getAbsolutePath());
					n.addChild(newNode);
					index(newNode);
				}
			}else{
				numFiles++;
				n.addFile(testMe);
			}
		}

	}
	
	public static void main(String []args){	
		Tree tree;
		try{
			File treeStore = new File("C:\\Users\\dc\\Desktop\\tree.stor");
			if(treeStore.exists()){
				//restore tree
				FileInputStream fis = new FileInputStream(treeStore);
				ObjectInputStream ins = new ObjectInputStream(fis);
				tree = (Tree)ins.readObject();
				
			}else{
				tree = new Tree();
				File f = new File("C:\\");
				tree.root.setFile(f);
				tree.index(tree.root);
				//tree.cleanup();
				System.out.println();
				FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\dc\\Desktop\\tree.stor"));
				ObjectOutputStream os = new ObjectOutputStream(fos);
				os.writeObject(tree);
				os.flush();
//				fos.close();
			}
	
			System.out.println("numChildren in Root:"+tree.root.numChild());
			System.out.println("numFiles in Root:"+tree.root.numFiles());
			System.out.println("numDirs:"+tree.numDirs);
			System.out.println("numFiles:"+tree.numFiles);
			System.out.println("date:"+tree.d);
			
			
			//breadth first display
			
			Vector<Node> v = tree.root.getChildren();
			System.out.println("size v:"+v.size());
			for(Node printMe:v){
				Vector<Node> c = printMe.getChildren();
				System.out.println("node children:"+c.size() + "NODE NAME:"+printMe.getFile().getAbsolutePath());
			}
			Vector<File> fi=tree.root.getFiles();
			for(File fileMe:fi){
				if(fileMe!=null){
				//System.out.println(fileMe.getAbsolutePath());
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
