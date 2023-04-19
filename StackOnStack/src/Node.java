
public class Node {
	private Node nextNode;
	private Node chainNode;
	private String data;
	private int value;
	public Node(int value,String data) {
		this.value = value;
		this.data = data;
		nextNode = null;
		chainNode = null;
	}
	public Node getNextNode() {
		return nextNode;
	}
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Node getChainNode() {
		return chainNode;
	}
	public void setChainNode(Node chainNode) {
		this.chainNode = chainNode;
	}
	
	
	
}
