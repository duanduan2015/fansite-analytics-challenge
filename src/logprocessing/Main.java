package logprocessing;
import tools.*;
import java.io.*;
import java.util.*;
public class Main {

    public static void main(String[] args) throws IOException {

        LogParser parser = new LogParser(args[0]);

        TopKActiveHostsAnalyser hostsAnalyser = new TopKActiveHostsAnalyser(10, new File(args[1]));
        TopKResourcesBandwidthAnalyser resourcesAnalyser = new TopKResourcesBandwidthAnalyser(10, new File(args[2]));
        TopKBusiestNMinutesAnalyser busiestHoursAnalyser = new TopKBusiestNMinutesAnalyser(10, 60, new File(args[3]));
        BlockNMinutesAnalyser blockAnalyser = new BlockNMinutesAnalyser(20, 5, 3, new File(args[4]));

        LogEntry entry = parser.nextEntry();

        while (entry != null) {
            hostsAnalyser.analyze(entry);
            resourcesAnalyser.analyze(entry);
            busiestHoursAnalyser.analyze(entry); 
            blockAnalyser.analyze(entry);
            entry = parser.nextEntry();
        }

        parser.close();

        hostsAnalyser.reportResults();
        resourcesAnalyser.reportResults();
        busiestHoursAnalyser.reportResults();
        blockAnalyser.reportResults();

    }
}