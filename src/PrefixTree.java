import java.util.*;

public class PrefixTree <K extends Segmentisable, V> {

    private HashMap<String, PrefixTree<K, V>> root;
    private HashMap<String, V> values;

    public PrefixTree() {
        this.root = new HashMap<String, PrefixTree<K, V>>();        
        this.values = new HashMap<String, V>();
    }

    public V get(Segmentisable key) {
        if (key.isLastLayer()) {
            if (values.containsKey(key.getTopLayerName())) {
                return values.get(key.getTopLayerName());     
            } else {
                return null;
            }
        }
        if (this.root.containsKey(key.getTopLayerName())) {
            return this.root.get(key.getTopLayerName()).get(key.nextSubSegments());
        }
        return null;
    }

    public void put(Segmentisable key, V value) {
        if (key.isLastLayer()) {
            values.put(key.getTopLayerName(), value);
        } else {
            if (this.root.containsKey(key.getTopLayerName())) {
                this.root.get(key.getTopLayerName()).put(key.nextSubSegments(), value);
            } else {
                PrefixTree<K, V> subTree = new PrefixTree<K, V>();
                subTree.put(key.nextSubSegments(), value);
                this.root.put(key.getTopLayerName(), subTree);
            }
        }
    }
}
