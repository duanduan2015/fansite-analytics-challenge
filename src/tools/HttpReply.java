package tools;
import java.util.*;

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
        return "Status code: " + Integer.toString(this.status)  + "," + " number of bytes: " + Long.toString(numOfBytes);
    }
}
