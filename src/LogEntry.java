import java.util.*;

public class LogEntry {

    private ClientAddress address;
    private String time; //TODO using Data instead
    private HttpRequest request;
    private HttpReply reply;

    public LogEntry(String[] info) {

        if (info[0].matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            this.address = new ClientIPv4Address(info[0]);
        } else {
            this.address = new ClientDomainNameAddress(info[0]);
        }
        this.time = info[1];
        if (info[2].equals("GET")) {
            this.request = new HttpGETRequest(info[2], info[3], info[4]);
        } else {
            this.request = new HttpPOSTRequest(info[2], info[3], info[4]);
        }

        this.reply = new HttpReply(info[5], info[6]);
        System.out.print(this.address.toString() + ",");
        System.out.print(this.time + ",");
        System.out.print(this.request.toString() + ",");
        System.out.print(this.reply.toString() + ",");
        System.out.print("\n");
    }
}
