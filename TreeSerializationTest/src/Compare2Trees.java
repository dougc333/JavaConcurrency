import java.util.*;


public class Compare2Trees {
	private static Tree t1;
	private static Tree t2;
	
	//compare the structure of 2 trees. 
	//comparator interface 0 for equal, 1 for greater than, -1 for less than
	public static void compare(){
		Node t1Root = t1.getRoot();
		while(noMoreChildren(t1Root)){
			
		}
	}
	
	//return true if there are no children
	public static boolean noMoreChildren(Node n){
		Vector<Node> children = n.getChildren();
		if(children==null){
			return true;
		}
		return false;
	}
	
	public static void main(String []args){
		//print out the differences in space and where it has grown between the 2 trees
		
	}
}
