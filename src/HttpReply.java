import java.util.*;

public class HttpReply {
    private int status;
    private long numOfBytes;
    private String replyString;

    public HttpReply(String s, String n) {
        this.status = Integer.parseInt(s);
        if (n.equals("-")) {
            this.numOfBytes = 0;
        } else {
            this.numOfBytes = Long.parseLong(n);
        }
        this.replyString = "Status code: " + s + "," + " number of bytes: " + n;
    }
    
    public int getStatusCode() {
        return this.status;
    }

    public long getNumOfBytes() {
        return this.numOfBytes;
    }

    public String toString() {
        return this.replyString;
    }
}
