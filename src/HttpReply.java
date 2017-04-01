import java.util.*;

public class HttpReply {
    private int status;
    private int numOfBytes;

    public HttpReply(String s, String n) {
        this.status = Integer.parseInt(s);
        if (n.equals("-")) {
            this.numBytes = 0;
        } else {
            this.numBytes = Integer.parseInt(n);
        }
    }
    
    public int getStatusCode() {
        return this.status;
    }

    public int getNumOfBytes() {
        return this.numOfBytes;
    }

}
