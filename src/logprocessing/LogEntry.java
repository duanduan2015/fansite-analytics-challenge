package logprocessing;
import tools.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
* The LogEntry class parse a string array to 
* specified objects and used for processing 
* and analysing.
*
* @author Yunduan Han 
*/

public class LogEntry {

    private ClientAddress address;
    private Date date; 
    private String dateString;
    private HttpRequest request;
    private HttpReply reply;
    private String entryString;

    /**
     * Parse the String array and get following:
     * ClientAddress object represents client address
     * Date object represents the access date
     * HttpRequest object represents the http request 
     * HttpReply object represents the http reply
     */
    public LogEntry(String[] info, String entryString) {

        this.entryString = entryString;

        if (info[0].matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            try {
                this.address = new ClientIPv4Address(info[0]);
            } catch (IllegalArgumentException e) {
                this.address = new ClientDomainNameAddress(info[0]);
            }
        } else {
            this.address = new ClientDomainNameAddress(info[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
        try {
            this.date = sdf.parse(info[1]);
            this.dateString = info[1];
        } catch (Exception e) {

        }
        this.request = HttpRequest.parseHttpRequest(info[2]);
        this.reply = new HttpReply(info[3], info[4]);
    }

    public ClientAddress getAddress() {
        return this.address;
    }

    public Date getAccessDate() {
        return this.date;
    }

    public HttpRequest getHttpRequest() {
        return this.request;
    }

    public HttpReply getHttpReply() {
        return this.reply;
    }

    public String getDateString() {
        return this.dateString;
    }
    
    public String getEntryString() {
        return this.entryString;
    }

    public String toString() {
        return String.format("log_entry(%s, time(%s), %s, %s)",
                this.address, this.date,
                this.request, this.reply);
    }

}
