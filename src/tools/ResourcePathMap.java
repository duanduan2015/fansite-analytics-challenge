package tools;
import java.util.*;

public class ResourcePathMap {
    private PrefixTree<ResourcePath, Long> resourceTree;

    public ResourcePathMap() {
        this.resourceTree = new PrefixTree<ResourcePath, Long>();
    }

    public boolean contains(ResourcePath path) {
        return this.resourceTree.contains(path);
    }

    public void put(ResourcePath path, Long value) {
        this.resourceTree.put(path, value);
    }

    public Long get(ResourcePath path) {
        return this.resourceTree.get(path);
    }

    public PrefixTree<ResourcePath, Long> getResourceTree() {
        return this.resourceTree;
    }
}
