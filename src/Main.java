import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        LogParser parser = new LogParser(args[0]);
        TopKActiveHostsAnalyser analyser = new TopKActiveHostsAnalyser(10, "hosts.txt");
        int i = 1;
        LogEntry entry = parser.nextEntry();
        while (entry != null) {
            System.out.println(i);
            analyser.analyze(entry);
            entry = parser.nextEntry();
            i++;
        }
        parser.close();
        analyser.reportResults();
    }
}

