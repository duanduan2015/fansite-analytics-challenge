package logprocessing;
import tools.*;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        LogParser parser = new LogParser(args[0]);
        ArrayList<Analyser> myAnalysers = new ArrayList<>();

        myAnalysers.add(new TopKActiveHostsAnalyser(10, new File(args[1])));
        myAnalysers.add(new TopKResourcesBandwidthAnalyser(10, new File(args[2])));
        myAnalysers.add(new TopKBusiestNMinutesAnalyser(10, 60, new File(args[3])));
        myAnalysers.add(new BlockNMinutesAnalyser(20, 5, 3, new File(args[4])));

        LogEntry entry = null;
        for (int i = 1;; i++) {
            try {
                entry = parser.nextEntry();
            } catch (IllegalArgumentException e) {
                //System.out.println("Warning: " + e.getMessage());
            }

            if (entry == null) {
                break;
            }

            for (Analyser a: myAnalysers) {
                a.analyze(entry);
            }
            if (i % 10000 == 0) {
                System.out.println("processed: " + i + " lines");
            }
        }

        parser.close();

        for (Analyser a: myAnalysers) {
            a.reportResults();
        }
    }
}
