package logprocessing;
import tools.*;
import java.util.*;
import java.io.*;

/**
* TopKResourcesBandwidthAnalyser implements
* the function to analyze the top k (10) 
* resources that have the most bandwidth.
*
* @author Yunduan Han 
*/
public class TopKResourcesBandwidthAnalyser implements Analyser {

    private int topK;
    private FileWriter writer;
    private ResourcePathMap resourcesMap;
    private PriorityQueue<ResourceBandwidth> queue;
    private Set<ResourceBandwidth> set;

    public TopKResourcesBandwidthAnalyser(int k, File file) throws IOException {
        this.topK = k;
        this.writer = new FileWriter(file); 
        this.resourcesMap = new ResourcePathMap();
        this.queue = new PriorityQueue<ResourceBandwidth>();
        this.set = new HashSet<ResourceBandwidth>();
    }

    @Override
    public void analyze(LogEntry entry) {
        HttpRequest httpRequest = entry.getHttpRequest();
        if (httpRequest == null) {
            return;
        }
        HttpReply httpReply = entry.getHttpReply();
        ResourcePath address = httpRequest.getResourcePath();
        long bytes = httpReply.getNumOfBytes();
        if (this.resourcesMap.contains(address)) {
            bytes += this.resourcesMap.get(address);
            this.resourcesMap.put(address, bytes);
        } else {
            this.resourcesMap.put(address, bytes);
        }
        ResourceBandwidth rb = new ResourceBandwidth(address.toString(), bytes);
        if (this.set.contains(rb)) {
            this.set.remove(rb);
            this.queue.remove(rb);
        }
        this.set.add(rb);
        this.queue.offer(rb);
        if (this.queue.size() > topK) {
            this.queue.poll();
        }
    }

    @Override
    public void reportResults() throws IOException {

        ArrayList<String> results = new ArrayList<String>();

        while (!this.queue.isEmpty()) {
            ResourceBandwidth rb = this.queue.poll();
            results.add(rb.getResourceName() + "\n");
        }

        for (int i = results.size() - 1; i >= 0; i--) {
            this.writer.write(results.get(i));
        }

        this.writer.close();
    }
}

/**
 * ResourceBandwidth is used to
 * store the resource name and 
 * total bandwidth information.
 */
class ResourceBandwidth implements Comparable<ResourceBandwidth> {

    private String resource;
    private long totalBandwidth;

    public ResourceBandwidth(String p, long b) {
        this.resource = p;
        this.totalBandwidth = b;
    }

    public String getResourceName() {
        return this.resource;
    }

    public long getTotalBandwidth() {
        return this.totalBandwidth;
    }

    @Override
    public int compareTo(ResourceBandwidth h) {
        if (this.totalBandwidth > h.getTotalBandwidth()) {
            return 1;
        }  
        if (this.totalBandwidth == h.getTotalBandwidth()) {
            return 0;
        }  
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceBandwidth)) {
            return false;
        }
        ResourceBandwidth p = (ResourceBandwidth) o;
        return p.getResourceName().equals(this.resource);
    }

    @Override
    public int hashCode() {
        return this.resource.hashCode();
    }
}
