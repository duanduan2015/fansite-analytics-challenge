import java.util.*;
import java.io.*;
public class TopKBusiestNMinutesAnalyser {
    private int topK;
    private FileWriter writer;
    private PriorityQueue<TimePeroid> queue;
    private LinkedList<LogEntry> list;
    private int minutes;
    private TimePeroid currentTimePeroid;

    public TopKBusiestNMinutesAnalyser(int k, int minutes, String outputPath) throws IOException {
        this.topK = k;
        this.writer = new FileWriter(outputPath); 
        this.queue = new PriorityQueue<TimePeroid>();
        this.list = new LinkedList<LogEntry>();
        this.minutes = minutes;
        this.currentTimePeroid = null;
    }

    public void analyze(LogEntry entry) {
        if (this.currentTimePeroid == null) {
            list.add(entry);
            this.currentTimePeroid = new TimePeroid(entry.getDateString(), entry.getAccessDate(), this.minutes);
        } else {
            if (this.currentTimePeroid.inTimePeroid(entry.getAccessDate())) {
                list.add(entry);
                this.currentTimePeroid.addOneAccess();
            } else {
                queue.offer(this.currentTimePeroid);
                Date start = this.currentTimePeroid.getStartTime();
                while (!list.isEmpty()) {
                    LogEntry e = list.peek();
                    if (e.getAccessDate().equals(start)) {
                        list.removeFirst();
                    } else {
                        this.currentTimePeroid = new TimePeroid(e.getDateString(), e.getAccessDate(), this.minutes);
                        this.currentTimePeroid.addNAccess(list.size() - 1);
                        break;
                    }
                }
            }
        }
        if (queue.size() > topK) {
            queue.poll();
        }
    }

    public void reportResults() throws IOException {
        for (int i = 0; i < topK; i++) {
            TimePeroid tp = this.queue.poll();
            this.writer.write(tp.getTimeString() + "," + Integer.toString(tp.getTotalAccessTimes()) + "\n");
        }
        this.writer.close();
    }
}

class TimePeroid implements Comparable<TimePeroid> {
    private String timeString;
    private Date startTime;
    private Date endTime;
    private int totalAccessTimes;

    public TimePeroid (String string, Date start, int minutes) {
        this.timeString = string;
        this.startTime = start;
        this.endTime = new Date(this.startTime.getTime() + (long) minutes * 60 * 1000);
        this.totalAccessTimes = 1;
        
    }

    public boolean inTimePeroid(Date d) {
        return this.endTime.after(d);
    }

    public void addOneAccess() {
        this.totalAccessTimes++;
    }

    public void addNAccess(int n) {
        this.totalAccessTimes += n;
    }

    public String getTimeString() {
        return this.timeString;
    }

    public int getTotalAccessTimes() {
        return this.totalAccessTimes;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    @Override
    public int compareTo(TimePeroid t) {
        return this.totalAccessTimes - t.getTotalAccessTimes();
    }
}
