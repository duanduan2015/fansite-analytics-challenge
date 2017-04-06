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

    private int topK;
    private FileWriter writer;
    private PriorityQueue<TimePeroid> queue;
    private LinkedList<LogEntry> list;
    private int minutes;
    private TimePeroid currentTimePeroid;

    public TopKBusiestNMinutesAnalyser(int k, int minutes, File file) throws IOException {
        this.topK = k;
        this.writer = new FileWriter(file); 
        this.queue = new PriorityQueue<TimePeroid>();
        this.list = new LinkedList<LogEntry>();
        this.minutes = minutes;
        this.currentTimePeroid = null;
    }

    @Override
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

    private void complementTopK() {

        if (this.list.size() == 0) {
            return;
        }

        LogEntry entry = this.list.poll();
        TimePeroid current = new TimePeroid(entry.getDateString(), 1, entry.getAccessDate());
        current.addNAccess(this.list.size());

        while (!this.list.isEmpty()) {
            LogEntry next = this.list.poll();
            if (next.getAccessDate().equals(current.getStartTime())) {
                continue;
            }
            this.queue.offer(current);
            current = new TimePeroid(next.getDateString(), 1, next.getAccessDate());
            current.addNAccess(this.list.size());
        }

        this.queue.offer(current);

        while (this.queue.size() > this.topK) {
            this.queue.poll();
        }

    }

    @Override
    public void reportResults() throws IOException {

        ArrayList<String> results = new ArrayList<String>();

        complementTopK();

        while (!this.queue.isEmpty()) {
            TimePeroid tp = this.queue.poll();
            results.add(tp.getTimeString() + "," + Integer.toString(tp.getTotalAccessTimes()) + "\n");
        }
        

        for (int i = results.size() - 1; i >= 0; i--) {
            this.writer.write(results.get(i));
        }

        this.writer.close();
    }
}

/**
 * TimePeroid is used to record the 
 * information of a 60 minutes peroid
 * in order to get a rank of every 60
 * minutes peroid.
 */
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
    
    public TimePeroid (String string, int accessTimes, Date date) {
        this.timeString = string;
        this.totalAccessTimes = accessTimes;
        this.startTime = date;
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
        if (this.totalAccessTimes != t.getTotalAccessTimes()) {
            return this.totalAccessTimes - t.getTotalAccessTimes();
        }
        return (int)(t.getStartTime().getTime() - this.startTime.getTime());
    }
}
