public class HttpPOSTRequest implements HttpRequest{
    private String method;
    private ResourcePath path;
    //private String version;
    private String requestString;
    public HttpPOSTRequest(String m, String p) {
        this.method = m;
        this.path = new ResourcePath(p);
        //this.version = v;
        this.requestString = m + " " + p;
    }
    public String getHttpMethodType() {
        return this.method;
    }
    public ResourcePath getResourcePath() {
        return this.path;
    }
    /*public String getHttpVersion() {
        return this.version;
    }*/
    public String toString() {
        return this.requestString; 
    }
}
