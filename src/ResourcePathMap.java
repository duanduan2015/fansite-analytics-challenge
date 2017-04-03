import java.util.*;

public class ResourcePathMap {
    private PrefixTree<ResourcePath, Integer> resourceTree;

    public ResourcePathMap() {
        this.resourceTree = new PrefixTree<ResourcePath, Integer>();
    }

    public boolean contains(ResourcePath path) {
        return this.resourceTree.contains(path);
    }

    public void put(ResourcePath path, Integer value) {
        this.resourceTree.put(path, value);
    }

    public Integer get(ResourcePath path) {
        return this.resourceTree.get(path);
    }

    public PrefixTree<ResourcePath, Integer> getResourceTree() {
        return this.resourceTree;
    }
}
