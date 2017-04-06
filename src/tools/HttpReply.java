package tools;
import java.util.*;

/**
 * HttpReply parses the code string 
 * and bytes string to generate a HttpReply 
 * object to represents a http reply.
 *
 * @author Yunduan Han
 */
public class HttpReply {
    private int status;
    private long numOfBytes;

    public HttpReply(String s, String n) {
        this.status = Integer.parseInt(s);
        if (n.equals("-")) {
            this.numOfBytes = 0;
        } else {
            this.numOfBytes = Long.parseLong(n);
        }
    }
    
    public int getStatusCode() {
        return this.status;
    }

    public long getNumOfBytes() {
        return this.numOfBytes;
    }

    public String toString() {
        return String.format("http_resp(code:%d, length:%d)", this.status, this.numOfBytes);
    }
}
