import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class LogEntry {

    private ClientAddress address;
    private Date date; 
    private HttpRequest request;
    private HttpReply reply;

    public LogEntry(String[] info) {

        if (info[0].matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            this.address = new ClientIPv4Address(info[0]);
        } else {
            this.address = new ClientDomainNameAddress(info[0]);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
        try {
            this.date = sdf.parse(info[1]);
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

}
