package logprocessing;
import java.util.*;
import tools.*;
import java.io.*;
public class BlockNMinutesAnalyser implements Analyser {
       
    private int detectSeconds;
    private int blockMinutes;
    private int maxFailedTimes;
    private FileWriter writer;
    private HashMap<String, Block> failedList;
	private static final ResourcePath LOGIN = new ResourcePath("/login");

    public BlockNMinutesAnalyser(int detectPeroid, int blockPeroid, int max, File file) throws IOException{
        this.writer = new FileWriter(file); 
        this.detectSeconds = detectPeroid;
        this.blockMinutes = blockPeroid;
        this.maxFailedTimes = max;
        this.failedList = new HashMap<String, Block>();
    }
    
    @Override
    public void analyze(LogEntry entry) {
        if (entry.getHttpRequest() == null) {
            return;
        }
        String hostName = entry.getAddress().toString();
        Date date = entry.getAccessDate();
        ResourcePath path = entry.getHttpRequest().getResourcePath();
        int status = entry.getHttpReply().getStatusCode();
        if (!failedList.containsKey(hostName)) {
            if (!path.equals(LOGIN) || status < 400) {
                return;
            } else {
                Block block = new Block(date, this.detectSeconds, this.blockMinutes, this.maxFailedTimes);
                block.addOneFailedRecord(date);
                failedList.put(hostName, block);
            }
            return;
        }
        if (failedList.containsKey(hostName)) {
            Block block = failedList.get(hostName);
            if (block.inDetectPeroid(date)) {
                if (block.canBlock()) {
                    try {
                        doBlock(entry);
                    } catch(IOException e) {
                        throw new IllegalArgumentException("Unable to block address'" + entry.getEntryString());
                    }
                } else {
                    if (!path.equals(LOGIN)) {
                        return;
                    }
                    if (status < 400) {
                        failedList.remove(hostName);
                    } else {
                        block.addOneFailedRecord(date);
                        if (block.canBlock()) {
                            block.setBlockStartTime(date);
                        }
                    }
                }
            } else {
                if (block.canBlock()) {
                    if (block.inBlockPeroid(date)) {
                        try {
                            doBlock(entry);
                        } catch(IOException e) {
                            throw new IllegalArgumentException("Unable to block address'" + entry.getEntryString());
                        }
                    } else {
                        failedList.remove(hostName);
                        if (path.equals(LOGIN) && status >= 400) {
                            Block newBlock = new Block(date, this.detectSeconds, this.blockMinutes, this.maxFailedTimes);
                            newBlock.addOneFailedRecord(date);
                            failedList.put(hostName, newBlock);
                        }
                    }
                } else {
                    if (!path.equals(LOGIN)) {
                        return;
                    }
                    if (status < 400) {
                        failedList.remove(hostName);
                    } else {
                        ArrayList<Date> failedDates = block.getFailedDates();
                        Block newBlock = new Block(date, 20, 5, 3);
                        for (int i = 0; i < failedDates.size(); i++) {
                            if (date.getTime() - failedDates.get(i).getTime() < 20) {
                                newBlock.addOneFailedRecord(failedDates.get(i));
                            }
                        }
                        newBlock.addOneFailedRecord(date);
                        newBlock.setDetectStartTime();
                        failedList.put(hostName, newBlock);
                        if (newBlock.canBlock()) {
                            newBlock.setBlockStartTime(date);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void reportResults() throws IOException {
        this.writer.close();
    }

    private void doBlock(LogEntry entry) throws IOException {
        this.writer.write(entry.getEntryString() + "\n");
    }
}

class Block {
    Date detectStartTime;
    Date detectEndTime;
    Date blockStartTime;
    Date blockEndTime;
    private ArrayList<Date> failedDates;
    private int blockMinutes;
    private int detectSeconds;
    private int maxFailedTimes;
    public Block(Date start, int detectSeconds, int blockMinutes, int maxFailedTimes) {
        this.detectSeconds = detectSeconds;
        this.detectStartTime = start;
        this.detectEndTime = new Date(detectStartTime.getTime() + detectSeconds * 1000);
        this.blockMinutes = blockMinutes;
        this.blockStartTime = null;
        this.blockEndTime = null;
        this.maxFailedTimes = maxFailedTimes;
        this.failedDates = new ArrayList<Date>();
    }

    public boolean inBlockPeroid(Date d) {
        return d.before(this.blockEndTime);
    }

    public boolean inDetectPeroid(Date d) {
        return d.before(this.detectEndTime);
    }

    public void addOneFailedRecord(Date d) {
        this.failedDates.add(d);
    }

    public ArrayList<Date> getFailedDates() {
        return this.failedDates;
    }

    public void setBlockStartTime(Date d) {
        this.blockStartTime = d;
        this.blockEndTime = new Date(d.getTime() + this.blockMinutes * 60 * 1000); 
    }

    public void setDetectStartTime() {
        this.detectStartTime = failedDates.get(0);
        this.detectEndTime = new Date(detectStartTime.getTime() + detectSeconds * 1000);
    }

    public boolean canBlock() {
        return this.failedDates.size() == this.maxFailedTimes;
    }
}
