import java.util.*;
public class DomainName implements Segmentisable {
    private List<String> segments;
    private String nextLayerString;
    public DomainName(String s) {
        //domain name of a client, for example: www.google.com
        String[] layers = s.split("\\.");
        int end = s.lastIndexOf('.');
        this.nextLayerString = s.substring(0, end);
        this.segments = new ArrayList<String>();
        for (int i = layers.length - 1; i >= 0; i--) {
           this.segments.add(layers[i]); 
        }
    }

    public List<String> getSegments() {
        return this.segments;
    }

    public DomainName nextSubSegments() {
        return new DomainName(this.nextLayerString);
    }

    public boolean isLastLayer() {
        return this.segments.size() == 1;
    }
    
    public String getTopLayerName() {
        return this.segments.get(0);
    }

    public int segmentsSize() {
        return this.segments.size();
    }
}
