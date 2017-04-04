import java.util.*;
import java.io.*;

public class TopKResourcesConsumeMostBandwidthAnalyser implements Analyser {

    private int topK;
    private FileWriter writer;
    private ResourcePathMap resourcesMap;
    private PriorityQueue<ResourceBandwidth> queue;
    private Set<ResourceBandwidth> set;

    public TopKResourcesConsumeMostBandwidthAnalyser(int k, String outputPath) throws IOException {
        this.topK = k;
        this.writer = new FileWriter(outputPath); 
        this.resourcesMap = new ResourcePathMap();
        this.queue = new PriorityQueue<ResourceBandwidth>();
        this.set = new HashSet<ResourceBandwidth>();
    }

    public void analyze(LogEntry entry) {
        HttpRequest httpRequest = entry.getHttpRequest();
        if (httpRequest == null) {
            return;
        }
        HttpReply httpReply = entry.getHttpReply();
        ResourcePath address = httpRequest.getResourcePath();
        long bytes = httpReply.getNumOfBytes();
        if (this.resourcesMap.contains(address) && this.resourcesMap.get(address) != null) {
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

    public void reportResults() throws IOException {
        for (int i = 0; i < topK; i++) {
            ResourceBandwidth rb = this.queue.poll();
            this.writer.write(rb.getResourceName() + "\n");
        }
        this.writer.close();
    }
}

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
