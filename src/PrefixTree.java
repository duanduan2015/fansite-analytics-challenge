import java.util.*;

public class PrefixTree <K extends Segmentisable, V> {

    private TreeNode root;
    private K key;

    public PrefixTree() {
       this.root = new TreeNode(); 
    }
    
    public boolean contains(K key) {
       List<String> segments = key.getSegments(); 
       TreeNode currentRoot = this.root;
       for (String s : segments) {
           if (currentRoot.children.containsKey(s)) {
               currentRoot = currentRoot.children.get(s);
           } else {
               return false;
           }
       }
       return true;
    }

    public void put(K key, V value) {
       List<String> segments = key.getSegments(); 
       TreeNode currentRoot = this.root;
       for (String s : segments) {
           if (!currentRoot.children.containsKey(s)) {
               currentRoot.addChild(s);
           }
           currentRoot = currentRoot.children.get(s);
       }
       currentRoot.setValue(value);
    }

    public V get(K key) {
       List<String> segments = key.getSegments(); 
       TreeNode currentRoot = this.root;
       for (String s : segments) {
           if (currentRoot.children.containsKey(s)) {
               currentRoot = currentRoot.children.get(s);
           } else {
               return null;
           }
       }
       return currentRoot.getValue();
    }

    class TreeNode {

        private HashMap<String, TreeNode> children;
        private V value;

        public TreeNode() {
            this.children = new HashMap<String, TreeNode>();
            this.value = null;
        }

        public TreeNode addChild(String childName) {
            TreeNode node = new TreeNode();
            this.children.put(childName, node);
            return node;
        }

        public boolean contains(String childName) {
            return this.children.containsKey(childName);
        }

        public void setValue(V v) {
            this.value = v;
        }
        
        public V getValue() {
            return this.value;
        }

        public String removeChild(String childName) {
            if (this.children.containsKey(childName)) {
                this.children.remove(childName);
                return childName;
            }
            return null;
        }

    }
}
