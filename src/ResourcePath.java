import java.util.*;
public class ResourcePath implements Segmentisable {
    private List<String> segments;
    private String pathString;
    private String nextLayerString;
    public ResourcePath(String s) {
        this.pathString = s;
        int start = s.indexOf('/');
        this.nextLayerString = s.substring(start + 1);
        String[] layers = s.split("/");
        this.segments = new ArrayList<String>();
        for (int i = 0; i < layers.length; i++) {
           if (layers[i].length() == 0) {
               continue;
           }
           segments.add(layers[i]); 
        }
    }

    public List<String> getSegments() {
        return this.segments;
    }

    public ResourcePath nextSubSegments() {
        return new ResourcePath(this.nextLayerString);
    }

    public int segmentsSize() {
        return this.segments.size();
    }

    public String getTopLayerName() {
        return this.segments.get(0);
    }

    public boolean isLastLayer() {
        return this.segments.size() == 1;
    }
    
    public String toString() {
        return pathString;
    }
}
