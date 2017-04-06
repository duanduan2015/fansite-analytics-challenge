package logprocessing;
import tools.*;
import java.io.*;
import java.util.*;

/**
* Main runs all the analysers to analyse and report 
* results
* @author Yunduan Han 
*/

public class Main {

    public static void main(String[] args) throws IOException {

        LogParser parser = new LogParser(args[0]);
        ArrayList<Analyser> myAnalysers = new ArrayList<>();

        myAnalysers.add(new TopKActiveHostsAnalyser(10, new File(args[1])));
        myAnalysers.add(new TopKResourcesBandwidthAnalyser(10, new File(args[2])));
        myAnalysers.add(new TopKBusiestNMinutesAnalyser(10, 60, new File(args[3])));
        myAnalysers.add(new BlockNMinutesAnalyser(20, 5, 3, new File(args[4])));

        LogEntry entry = parser.nextEntry();
        int i = 0;
        while (entry != null) {
            for (Analyser a: myAnalysers) {
                a.analyze(entry);
            }
            i++;
            if (i % 10000 == 0) {
                System.out.println("processed: " + i + " lines");
            }
            entry = parser.nextEntry();
        }

        parser.close();

        for (Analyser a: myAnalysers) {
            a.reportResults();
        }
    }
}
