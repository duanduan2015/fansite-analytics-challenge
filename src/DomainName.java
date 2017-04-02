import java.util.*;
public class DomainName implements Segmentisable {
    private List<String> segments;
    private String name;
    public DomainName(String s) {
        this.name = s;
        String[] layers = s.split("\\.");
        this.segments = new ArrayList<String>();
        for (int i = layers.length - 1; i >= 0; i--) {
           this.segments.add(layers[i]); 
        }
    }

    public List<String> getSegments() {
        return this.segments;
    }

    public String toString() {
        return this.name;
    }
}
