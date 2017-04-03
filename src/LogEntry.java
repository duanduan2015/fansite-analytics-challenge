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

        if (info[2].equals("GET")) {
            this.request = new HttpGETRequest(info[2], info[3], info[4]);
        } else {
            this.request = new HttpPOSTRequest(info[2], info[3], info[4]);
        }

        this.reply = new HttpReply(info[5], info[6]);

        /*System.out.print(this.address.toString() + ",");
        System.out.print(this.date + ",");
        System.out.print(this.request.toString() + ",");
        System.out.print(this.reply.toString());
        System.out.print("\n");*/
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
