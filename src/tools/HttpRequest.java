package tools;
import java.util.regex.*;

/**
 * HttpRquest parses the a string 
 * to get the http method and the 
 * resource path to generate a 
 * HttpRequest object for further 
 * analyzing.
 *
 * @author Yunduan Han
 */
public class HttpRequest {

    private String method;
    private ResourcePath path;
    private String requestString;
    private static final Pattern REGEX_PATTERN = Pattern.compile("^\\s*(\\S+)\\s+([\\S&&[^\\?]]+)(?:\\?\\S*)?(?:\\s.*)?$");

    public static HttpRequest parseHttpRequest(String s) {
        Matcher matcher = REGEX_PATTERN.matcher(s);
        if (matcher.find()) {
            return new HttpRequest(matcher.group(1), matcher.group(2));
        } else {
            return null;
        }
    }

    public HttpRequest(String m, String p) {
        this.path = new ResourcePath(p);
        this.method = m;
        this.requestString = m + " " + p;
    }

    public String getHttpMethodType() {
        return this.method;
    }

    public ResourcePath getResourcePath() {
        return this.path;
    }

    public String toString() {
        //return this.requestString; 
		return String.format("http_req(method:%s, path:%s)",
				this.method, this.path == null ? "(none)" : this.path.toString());
    }
}
