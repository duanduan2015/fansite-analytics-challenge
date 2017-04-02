public class HttpPOSTRequest implements HttpRequest{
    private String method;
    private ResourcePath path;
    private String version;
    private String requestString;
    public HttpPOSTRequest(String m, String p, String v) {
        this.method = m;
        this.path = new ResourcePath(p);
        this.version = v;
        this.requestString = m + " " + p + " " + v;
    }
    public String getHttpMethodType() {
        return this.method;
    }
    public ResourcePath getResourcePath() {
        return this.path;
    }
    public String getHttpVersion() {
        return this.version;
    }
    public String toString() {
        return this.requestString; 
    }
}
