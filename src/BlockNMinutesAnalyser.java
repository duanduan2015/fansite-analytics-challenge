import java.util.*;
import java.io.*;
public class BlockNMinutesAnalyser implements Analyser {
       
    private int detectSeconds;
    private int blockMinutes;
    private int maxFailedTimes;
    private FileWriter writer;
    private ArrayList<String> blockList;
    private HashMap<String, Block> failedList;
	private static final ResourcePath LOGIN = new ResourcePath("/login");

    public BlockNMinutesAnalyser(String output, int detectPeroid, int blockPeroid, int max) throws IOException{
        this.writer = new FileWriter(output); 
        this.detectSeconds = detectPeroid;
        this.blockMinutes = blockPeroid;
        this.maxFailedTimes = max;
        this.blockList = new ArrayList<String>();
        this.failedList = new HashMap<String, Block>();
    }

    public void analyze(LogEntry entry) {
        if (entry.getHttpRequest() == null) {
            return;
        }
        String hostName = entry.getAddress().toString();
        Date date = entry.getAccessDate();
        ResourcePath path = entry.getHttpRequest().getResourcePath();
        int status = entry.getHttpReply().getStatusCode();
        if (!failedList.containsKey(hostName)) {
            if (status < 400) {
                return;
            } else {
                Block block = new Block(hostName, date, this.detectSeconds, this.blockMinutes, this.maxFailedTimes);
                failedList.put(hostName, block);
            }
            return;
        }
        if (failedList.containsKey(hostName)) {
            Block block = failedList.get(hostName);
            if (block.inDetectPeroid(date)) {
                if (block.canBlock()) {
                    blockList.add(entry.getEntryString());
                } else {
                    if (status < 400) {
                        failedList.remove(hostName);
                    } else {
                        block.addOneFailedRecord();
                        if (block.canBlock()) {
                            block.setBlockStartTime(date);
                        }
                    }
                }
            } else {
                if (block.canBlock()) {
                    if (block.inBlockPeroid(date)) {
                        blockList.add(entry.getEntryString());
                    } else {
                        if (status < 400) {
                            failedList.remove(hostName);
                        } else {
                            Block newBlock = new Block(hostName, date, this.detectSeconds, this.blockMinutes, this.maxFailedTimes);
                            failedList.put(hostName, newBlock);
                        }
                    }
                } else {
                    if (status < 400) {
                        failedList.remove(hostName);
                    } else {
                        Block newBlock = new Block(hostName, date, 20, 5, 3);
                        failedList.put(hostName, newBlock);
                    }
                }
            }
        }
    }

    public void reportResults() throws IOException {
        for (String s : blockList) {
            this.writer.write(s + "\n");
        }
        this.writer.close();
    }
}

class Block {
    private String hostName;
    Date detectStartTime;
    Date detectEndTime;
    Date blockStartTime;
    Date blockEndTime;
    private int blockMinutes;
    private int failedTimes;
    private int maxFailedTimes;
    public Block(String name, Date start, int detectSeconds, int blockMinutes, int maxFailedTimes) {
        this.hostName = name;
        this.detectStartTime = start;
        this.detectEndTime = new Date(detectStartTime.getTime() + detectSeconds * 1000);
        this.blockMinutes = blockMinutes;
        this.blockStartTime = null;
        this.blockEndTime = null;
        this.failedTimes = 1;
        this.maxFailedTimes = maxFailedTimes;
    }

    public boolean inBlockPeroid(Date d) {
        return d.before(this.blockEndTime);
    }

    public boolean inDetectPeroid(Date d) {
        return d.before(this.detectEndTime);
    }

    public void addOneFailedRecord() {
        this.failedTimes++;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setBlockStartTime(Date d) {
        this.blockStartTime = d;
        this.blockEndTime = new Date(d.getTime() + this.blockMinutes * 60 * 1000); 
    }

    public boolean canBlock() {
        return this.failedTimes == this.maxFailedTimes;
    }
}
