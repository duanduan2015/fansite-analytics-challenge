import java.util.*;
public class DomainName implements Segmentisable {
    private List<String> segments;
    public DomainName(String s) {
        //domain name of a client, for example: www.google.com
        String[] layers = s.split("\\.");
        this.segments = new ArrayList<String>();
        for (int i = layers.length - 1; i >= 0; i--) {
           this.segments.add(layers[i]); 
        }
    }

    public List<String> getSegments() {
        return this.segments;
    }

    public int segmentsSize() {
        return this.segments.size();
    }
}
