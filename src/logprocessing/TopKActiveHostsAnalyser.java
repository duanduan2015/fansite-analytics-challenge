package logprocessing;
import tools.*;
import java.util.*;
import java.io.*;

/**
* TopKActiveHostsAnalyser implements the function
* to analyse every entry and get the top K (10)
* most active client address in realtime.
*
* @author Yunduan Han 
*/

public class TopKActiveHostsAnalyser implements Analyser {

    private int topK;
    private FileWriter writer;
    private ClientAddressMap hostsMap;
    private PriorityQueue<HostActiveness> activeQueue;
    private Set<HostActiveness> set;

    public TopKActiveHostsAnalyser(int k, File file) throws IOException {
        this.topK = k;
        this.writer = new FileWriter(file); 
        this.hostsMap = new ClientAddressMap();
        this.activeQueue = new PriorityQueue<HostActiveness>();
        this.set = new HashSet<HostActiveness>();
    }

    @Override
    /**
     * Analyze one entry and get current rank for
     * top k (10) most active clients.
     */
    public void analyze(LogEntry entry) {
        ClientAddress address = entry.getAddress();
        int time = 0;
        if (this.hostsMap.contains(address)) {
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

    @Override
    /**
     * Report the final result to the output file.
     */
    public void reportResults() throws IOException {

        ArrayList<String> results = new ArrayList<String>();

        while (!this.activeQueue.isEmpty()) {
            HostActiveness ha = this.activeQueue.poll();
            results.add(ha.getHostName() + "," + Integer.toString(ha.getAccessTimes()) + "\n");
        }

        for (int i = results.size() - 1; i >= 0; i--) {
            this.writer.write(results.get(i));
        }

        this.writer.close();
    }
}

/**
 * HostActiveness is used to store the host name and its
 * access times.
 */

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
