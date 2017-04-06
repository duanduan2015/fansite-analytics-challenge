package logprocessing;
import tools.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LogParser {

    private BufferedReader bufferedReader;
    private static Pattern REGEX_PATTERN;

    public LogParser(String path) throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(new File(path)));
    }

    public void close() throws IOException {
        this.bufferedReader.close();
    }

    public LogEntry nextEntry() throws IOException {
        String line = this.bufferedReader.readLine();
        if (line != null) {
            Matcher m = REGEX_PATTERN.matcher(line);
            int count = m.groupCount();
            String[] info = new String[count];
            if (m.find()) {
                for (int i = 0; i < count; i++) {
                    info[i] = m.group(i + 1);
                }
            } else {
                //info[0] = line.split(" ")[0];    
                throw new IllegalArgumentException("Unable to parse line '" + line + "'");
            }
            return new LogEntry(info, line);
        }
        return null;
    }

    public static LogEntry parseLogEntry(String s) {
        String line = s;
        if (line != null) {
            Matcher m = REGEX_PATTERN.matcher(line);
            int count = m.groupCount();
            String[] info = new String[count];
            if (m.find()) {
                for (int i = 0; i < count; i++) {
                    info[i] = m.group(i + 1);
                }
            } else {
                throw new IllegalArgumentException("Unable to parse line '" + line + "'");
            }
            return new LogEntry(info, line);
        }
        return null;
    }

    private static void buildRegexPattern() {
        String reClientAddress = "(\\S+)";
        String reDoubleDash = "-\\s+-";
        String reTimestamp = "\\[(.+)\\]";
        String reHTTPRequest = "[\"“](.*)[\"”]";
        String reHTTPResponse = "(\\d+)\\s+([\\d-]+)";
        String reAssembled = "^\\s*" + reClientAddress +
            "\\s*" + reDoubleDash + "\\s*" + reTimestamp +
            "\\s*" + reHTTPRequest + "\\s*" + reHTTPResponse + "\\s*$";

        REGEX_PATTERN = Pattern.compile(reAssembled);
    }

    static {
        buildRegexPattern();
    }
}
