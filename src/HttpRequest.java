public class HttpRequest {
    private String method;
    private ResourcePath path;
    private String requestString;

    public HttpRequest(String m, String p) {
        this.method = m;
        this.path = new ResourcePath(p);
        this.requestString = m + " " + p;
    }

    public String getHttpMethodType() {
        return this.method;
    }

    public ResourcePath getResourcePath() {
        return this.path;
    }

    public String toString() {
        return this.requestString; 
    }
}
