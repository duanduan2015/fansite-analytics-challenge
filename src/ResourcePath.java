import java.util.*;
public class ResourcePath implements Segmentisable {

    private List<String> segments;
    private String pathString;

    public ResourcePath(String s) {
        this.pathString = s;
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

    public String toString() {
        return pathString;
    }
}
