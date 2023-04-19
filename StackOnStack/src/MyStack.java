import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
public class MyStack {
	public static void main(String[] args) throws IOException {
		MyStack app = new MyStack();
//		if(app.IsEmpty()) {
//			System.out.println("The stack is still empty!");
//		}else {
//			System.out.println("The stack is not empty!");
//		}
//		Node newNode = new Node(1,"LALA");
//		app.Push(newNode);
//		newNode = new Node(2,"LOLO");
//		app.Push(newNode);
//		newNode = new Node(3,"LELE");
//		app.Push(newNode);
//		newNode = new Node(4,"LULU");
//		app.Push(newNode);
//		app.Show();
//		System.out.println();
//		app.showChild(1);
//		System.out.println();
//		app.pushChild(new Node(4,"LOL"));
//		app.showChild(4);
//		System.out.println();
//		app.removeChild(4);
//		app.showChild(4);
//		System.out.println();
//		app.pushChild(new Node(1,"Halo jo"));
//		app.showChild(1);
		//Instantiating the URL class
      URL url = new URL("https://www.msn.com/id-id");
      //Retrieving the contents of the specified page
      Scanner sc = new Scanner(url.openStream());
      //Instantiating the StringBuffer class to hold the result
      StringBuffer sb = new StringBuffer();
      while(sc.hasNext()) {
         sb.append(sc.next());
         //System.out.println(sc.next());
      }
      //Retrieving the String from the String Buffer object
      String result = sb.toString();
      System.out.println(result);
      //Removing the HTML tags
      String[] links = result.split("href=");
      System.out.println();
      System.out.println();
      System.out.println(links[2]);
//      for(int i=0;i<links.length;i++) {
//    	  links[i] = links[i].substring(1);
//    	  links[i] = links[i].substring(0, links[i].lastIndexOf("\""));
//      }
      System.out.println();
      System.out.println();
      links[2] = links[2].substring(1);
      links[2] = links[2].substring(0, links[2].indexOf("\""));
      System.out.println(links[2]+"okeh");
      
//      result = result.replaceAll("<[^>]*>", "");
//      System.out.println("Contents of the web page: "+result);
      
//      String test = result.toString();
//      String[] links = test.split("href");
//      System.out.println(links[0]);
      
		
	}
	private Node top;
	public MyStack() {
		top = null;
	}
	public boolean IsEmpty() {
		return (top == null);
	}
	public void pushChild(Node child) {
		Node parent = getParent(child.getValue());
		if(parent.getChainNode()==null) {
			parent.setChainNode(child);
		}else {
			Node temp = parent.getChainNode();
			while(temp.getChainNode()!=null) {
				temp = temp.getChainNode();
			}
			temp.setChainNode(child);
		}
	}
	public void showChild(int value) {
		Node parent = getParent(value);
		while(parent!= null) {
			System.out.println(parent.getData());
			parent = parent.getChainNode();
		}
	}
	public void removeChild(int value) {
		Node parent = getParent(value);
		if(parent.getChainNode()== null) {
			System.out.println("No child");
		}else {
			while(parent.getChainNode().getChainNode()!= null) {
				parent = parent.getChainNode();
			}
			parent.setChainNode(null);
		}
	}
	public Node getParent(int value) {
		Node dummy =null;
		Node temp = top;
		while(temp!= null) {
			if(temp.getValue()==value)dummy=temp;
			temp = temp.getNextNode();
		}
		return dummy;
	}
	public void Show() {
		Node temp = top; //temp variabel dengan tipe Node diisi dengan top, digunakan hanya saat dipanggil, nanti kalau selesaimaka diambil dengan garbage collector
		while(temp != null) {
			System.out.println(temp.getData());
			temp = temp.getNextNode();
		}
	}
	public void Push(Node nodeToAdd) {
		if(top == null) {
			top = nodeToAdd;
		}else {
			nodeToAdd.setNextNode(top);
			top = nodeToAdd;
		}
	}
	public void Pop() {
		if(top == null) {
			System.out.println("Stack empty");
		}
		else if(top.getNextNode() == null) {
			top = null;
		}else {
			Node temp = top.getNextNode();
			top.setNextNode(temp.getNextNode());
			top = temp;
		}
	}
	public Node Peek() {
		Node temp = top;
		return temp;
	}
	public <T> boolean Contains(T data) {
		Node temp = top;
		boolean output = false;
		while(temp != null) {
			if(temp.getData().equals(data)) {
				output = true;
			}
			temp = temp.getNextNode();
		}
		return output;
	}
	public int Count() {
		int index=0;
		Node temp =top;
		while(temp != null) {
			index++;
			temp = temp.getNextNode();
		}
		return index;
	}
	public void Clear() {
		while(top!= null) {
			Pop();
		}
	}
}
