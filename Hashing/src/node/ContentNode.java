package node;
public class Node<T> {
    private Node<T> nextNode;
    private Node<T> chainNode;
    private T word;
    private int occurence;
    private T value;
    private Node<T> url;
    public Node(T word, T url) {
        this.word = word;
        this.url = url;
        this.occurence = 0;
        nextNode = null;
        chainNode = null;
    }
    public Node<T> getNextNode() {
        return nextNode;
    }
    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }
    public T getWord() {
        return this.word;
    }

    public T getURL() { return this.url; }

    public int getOccurence() {
        return occurence;
    }

    public void addOccurence() {
        this.occurence++;
    }
}
