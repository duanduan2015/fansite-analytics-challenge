import java.util.*;

public class LogEntry {

    private ClientAddress address;
    private String time; //TODO using Data instead
    private HttpRequest request;
    private HttpReply reply;

    public LogEntry(String s) {
        String[] strings = s.split(" ");
        strings[3] = strings[3].substring(1);
        strings[5] = strings[5].substring(1);
        strings[7] = strings[7].substring(0, strings[7].length() - 1);
        this.time = strings[3];
        if (strings[0].matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            this.address = new ClientIPv4Address(strings[0]);
        } else {
            this.address = new ClientDomainNameAddress(strings[0]);
        }
        if (strings[5].equals("GET")) {
            this.request = new HttpGETRequest(strings[5], strings[6], strings[7]);
        } else {
            this.request = new HttpPOSTRequest(strings[5], strings[6], strings[7]);
        }
        this.reply = new HttpReply(strings[strings.length - 2], strings[strings.length - 1]);
    }
}
