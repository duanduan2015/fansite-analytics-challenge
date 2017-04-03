import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        String inputPath = args[0];
        LogParser parser = new LogParser(inputPath);
        LogEntry entry = parser.nextEntry();
        ClientAddressMap clientAccessTimesMap = new ClientAddressMap();
        PriorityQueue<HostActiveness> activeQueue = new PriorityQueue<HostActiveness>();
        Set<HostActiveness> set = new HashSet<HostActiveness>();
        while (entry != null) {
            ClientAddress address = entry.getAddress();
            //System.out.println(address.toString());
            int time = 0;
            if (clientAccessTimesMap.contains(address)) {
                int accessTimes = clientAccessTimesMap.get(address);
                clientAccessTimesMap.put(address, accessTimes + 1);
                time = accessTimes + 1;
                //System.out.println(address.toString() + ":" + Integer.toString(accessTimes + 1));
            } else {
                clientAccessTimesMap.put(address, 1);
                time = 1;
                //System.out.println(address.toString() + ":" + Integer.toString(1));
            }
            HostActiveness ha = new HostActiveness(address.toString(), time);
            if (set.contains(ha)) {
                set.remove(ha);
                activeQueue.remove(ha);
            }
            set.add(ha);
            activeQueue.offer(ha);
            if (activeQueue.size() > 10) {
                activeQueue.poll();
            }
            entry = parser.nextEntry();
        }
        parser.close();
        while (activeQueue.size() > 0) {
            HostActiveness ha = activeQueue.poll();
            System.out.println(ha.getHostName() + ":" + Integer.toString(ha.getAccessTimes()));
        }
    }
}

class HostActiveness implements Comparable<HostActiveness> {
    private String host;
    private int accessTimes;
    public HostActiveness(String h, int t) {
        this.host = h;
        this.accessTimes = t;
    }

    public String getHostName() {
        return this.host;
    }

    public int getAccessTimes() {
        return this.accessTimes;
    }
    @Override
    public int compareTo(HostActiveness h) {
        return this.accessTimes - h.getAccessTimes();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HostActiveness)) {
            return false;
        }
        HostActiveness h = (HostActiveness) o;
        return h.getHostName().equals(this.host);
    }
    @Override
    public int hashCode() {
        return this.host.hashCode();
    }
}
