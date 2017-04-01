public class HttpPOSTRequest {
    private String method;
    private ResourcePath path;
    private String version;
    public HttpPOSTRequest(String m, String p, String v) {
        this.method = m;
        this.path = new ResourcePath(p);
        this.version = v;
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
}