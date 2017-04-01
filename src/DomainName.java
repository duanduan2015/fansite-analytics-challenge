public class DomainName implements Segmentisable {
    private List<String> segments;
    public DomainName(String s) {
        //domain name of a client, for example: www.google.com
        String[] layers = s.split("\\.");
        this.segments = new ArrayList<String>(layers.length);
        for (int i = layers.length; i>= 0; i--) {
           segments.add(layers[i]); 
        }
    }

    public List<String> getSegments() {
        return this.segments;
    }

    public int segmentsSize() {
        return this.segments.size();
    }
}
