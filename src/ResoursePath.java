public class ResourcePath implements Segmentisable {
    private List<String> segments;
    public ResourcePath(String s) {
        //resource path, for example: /login 
        String[] layers = s.split("/");
        this.segments = new ArrayList<String>(layers.length);
        for (int i = 0; i < layers.length; i++) {
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
