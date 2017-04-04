import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        LogParser parser = new LogParser(args[0]);
        //TopKActiveHostsAnalyser hostsAnalyser = new TopKActiveHostsAnalyser(10, "hosts.txt");
        //TopKResourcesConsumeMostBandwidthAnalyser resourcesAnalyser = new TopKResourcesConsumeMostBandwidthAnalyser(10, "resources.txt");
        //TopKBusiestNMinutesAnalyser busiestHoursAnalyser = new TopKBusiestNMinutesAnalyser(10, 60, "hours.txt");
        BlockNMinutesAnalyser blockAnalyser = new BlockNMinutesAnalyser("blocked.txt", 20, 5, 3);
        int i = 1;
        LogEntry entry = parser.nextEntry();
        while (entry != null) {
            if (i % 10000 == 0) {
                System.out.println(i);
            }
            //hostsAnalyser.analyze(entry);
            //resourcesAnalyser.analyze(entry);
            //busiestHoursAnalyser.analyze(entry); 
            blockAnalyser.analyze(entry);
            entry = parser.nextEntry();
            i++;
        }
        parser.close();
        //hostsAnalyser.reportResults();
        //resourcesAnalyser.reportResults();
        //busiestHoursAnalyser.reportResults();
        blockAnalyser.reportResults();
    }
}

