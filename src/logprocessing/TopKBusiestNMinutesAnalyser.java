package logprocessing;
import tools.*;
import java.util.*;
import java.io.*;

/**
* TopKBusiestNMinutesAnalyser implements the function
* to analyse every entry and get the top K (10)
* busiest hour peroid resources. 
*
* @author Yunduan Han 
*/
public class TopKBusiestNMinutesAnalyser implements Analyser {

    private AccessList accessList;
    private FileWriter writer;

    public TopKBusiestNMinutesAnalyser(int k, int minutes, File file) throws IOException {
        this.writer = new FileWriter(file); 
        this.accessList = new AccessList(k, minutes * 60000L);
    }

    @Override
    public void analyze(LogEntry entry) {

        long timestamp = entry.getAccessDate().getTime();
        String dateString = entry.getDateString();

        accessList.add(timestamp, dateString);
    }

    @Override
    public void reportResults() throws IOException {

        accessList.forceFillRank();

        for (AccessList.BusyPeriod bp: this.accessList.toList()) {
            this.writer.write(String.format("%s,%d%n", bp.getStartString(),
                        bp.getNumberOfAccess()));
        }

        this.writer.close();
    }
}

class AccessList {

    private final long PERIOD_LIMIT;
    private final int RANKING_LIMIT;
    private int count;
    private LinkedList<Access> accessList;
    private PriorityQueue<Access> accessRank;

    static public class BusyPeriod {
        private String start;
        private int nAccess;

        BusyPeriod(String start, int nAccess) {
            this.start = start;
            this.nAccess = nAccess;
        }

        public String getStartString() {
            return start;
        }

        public int getNumberOfAccess() {
            return nAccess;
        }
    }

    static private class Access implements Comparable<Access>{
        long timestamp;
        int count;
        String dateString;

        Access(long timestamp, int initCount, String dateString) {
            this.timestamp = timestamp;
            this.count = initCount;
            this.dateString = dateString;
        }

        public int compareTo(Access other) {
            return this.count > other.count ? 1 :
                this.count < other.count ? -1 : 0;
        }
    }

    public AccessList(int size, long period) {
        this.PERIOD_LIMIT = period;
        this.RANKING_LIMIT = size;
        this.count = 0;
        this.accessList = new LinkedList<Access>();
        this.accessRank = new PriorityQueue<Access>();
    }

    public long getOldest() {
        return accessList.getFirst().timestamp;
    }

    public void add(long ts, String raw) {

        while (accessList.size() > 0 && ts - accessList.getFirst().timestamp >= this.PERIOD_LIMIT) {
            Access evicted = accessList.pollFirst();
            this.count -= evicted.count;
            evicted.count += this.count;

            accessRank.offer(evicted);
            if (accessRank.size() > RANKING_LIMIT) {
                accessRank.poll();
            }
        }

        if (accessList.size() == 0 || accessList.getLast().timestamp != ts) {
            accessList.addLast(new Access(ts, 1, raw));
        } else {
            accessList.getLast().count += 1;
        }
        this.count += 1;
    }

    public int size() {
        return count;
    }

    public List<BusyPeriod> toList() {
        LinkedList<BusyPeriod> result = new LinkedList<BusyPeriod>();
        while (accessRank.size() > 0) {
            Access access = accessRank.poll();
            result.addFirst(new BusyPeriod(access.dateString, access.count));
        }
        return Collections.unmodifiableList(result);
    }

    public void forceFillRank() {

        int accessLeft = count;
        for (Access oldItem: accessList) {
            accessRank.offer(new Access(oldItem.timestamp, accessLeft, oldItem.dateString));
            if (accessRank.size() > RANKING_LIMIT) {
                accessRank.poll();
            }
            accessLeft -= oldItem.count;
        }
    }
}

