import java.util.*;
import java.io.*;
public class TopKActiveHostsAnalyser implements Analyser {

    private int topK;
    private FileWriter writer;
    private ClientAddressMap hostsMap;
    private PriorityQueue<HostActiveness> activeQueue;
    private Set<HostActiveness> set;

    public TopKActiveHostsAnalyser(int k, String outputPath) throws IOException {
        this.topK = k;
        this.writer = new FileWriter(outputPath); 
        this.hostsMap = new ClientAddressMap();
        this.activeQueue = new PriorityQueue<HostActiveness>();
        this.set = new HashSet<HostActiveness>();
    }

    public void analyze(LogEntry entry) {
        ClientAddress address = entry.getAddress();
        int time = 0;
        if (this.hostsMap.contains(address) && this.hostsMap.get(address) != null) {
            int accessTimes = this.hostsMap.get(address);
            this.hostsMap.put(address, accessTimes + 1);
            time = accessTimes + 1;
        } else {
            this.hostsMap.put(address, 1);
            time = 1;
        }
        HostActiveness ha = new HostActiveness(address.toString(), time);
        if (this.set.contains(ha)) {
            this.set.remove(ha);
            this.activeQueue.remove(ha);
        }
        this.set.add(ha);
        this.activeQueue.offer(ha);
        if (this.activeQueue.size() > topK) {
            this.activeQueue.poll();
        }
    }

    public void reportResults() throws IOException {
        for (int i = 0; i < topK; i++) {
            HostActiveness ha = this.activeQueue.poll();
            this.writer.write(ha.getHostName() + "," + Integer.toString(ha.getAccessTimes()) + "\n");
        }
        this.writer.close();
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