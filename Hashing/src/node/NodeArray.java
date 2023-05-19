package node;

public class NodeArray<T> {
    private int size;
    private int lastAccessed;
    private Node[] nodeArray;

    public NodeArray(int size) {
        this.nodeArray = new Node[size];
        this.lastAccessed = 0;
    }

    public Node getNode(int index) {
        return this.nodeArray[index];
    }

    public void insertNode(int index, Node<T> node) {
        Node<T> temp = this.nodeArray[index];

        if(isExist(index, (String)node.getWord())) {
            temp.addOccurence();
            return;
        }

        if(temp == null) {
            this.nodeArray[index] = node;
        } else if(temp.getNextNode()==null){
            temp.setNextNode(node);
        }
        else {
            while(temp.getNextNode()!=null){
                temp = temp.getNextNode();
            }
            temp.setNextNode(node);
        }
    }
    public void showAll(int index){
        Node<T> temp = this.nodeArray[index];
        while(temp!=null){
            System.out.println(temp.getWord());
            temp = temp.getNextNode();
        }
    }

    public void show(String word) {
        final int index = getHashCode(word, 300);
        final String indexString = "Index : ";
        final String keyString = "Key : ";
        final String URLString = "URL : ";
        final String occurence = "Occurence : ";

        Node<T> temp = this.nodeArray[index];
        String formed = "";
        if(temp.equals(word)) {
            formed = String.format("%s%d\n%s%s\n%s%s\n%s%d\n",
                    indexString,
                    index,
                    keyString,
                    temp.getWord(),
                    URLString,
                    temp.getURL(),
                    occurence,
                    temp.getOccurence());
            System.out.println(formed);
        } else {
            while(temp != null) {
                temp = temp.getNextNode();
                if(temp != null) {
                    if (temp.getWord().equals(word)) {
                        formed = String.format("%s%d\n%s%s\n%s%s\n%s%d\n", indexString, index, keyString, temp.getWord(), URLString, temp.getURL(), occurence, temp.getOccurence());
                        break;
                    }
                }
            }
        }

        System.out.println(formed);
    }

    public Node<T> getNodeFromKey(String key, int length) {
        int hashValue = getHashCode(key, length);
        return nodeArray[hashValue];
    }

    public int getHashCode(String word, int length) {
        int hashValue = 0;
        for(int i = 0; i < word.length(); i++) {
            hashValue += word.charAt(i);
        }
        hashValue %= length;

        return hashValue;
    }

    private boolean isExist(int index, String word) {
        Node temp = nodeArray[index];

        if(temp == null) {
            return false;
        }

        if(temp.getNextNode() == null) {
            return temp.getWord().equals(word);
        } else {
            while(temp != null){
                if(temp.getWord().equals(word)) {
                    return true;
                } else {
                    temp = temp.getNextNode();
                }
            }
        }

        return false;
    }
}
