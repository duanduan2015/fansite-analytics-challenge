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
            if (m.find()) {
                int count = m.groupCount();
                String[] info = new String[count];
                for (int i = 0; i < count; i++) {
                    info[i] = m.group(i + 1);
                }
                return new LogEntry(info);
            } else {
                throw new IllegalArgumentException("Unable to parse line '" + line + "'");
            }
        }
        return null;
    }

    private static void buildRegexPattern() {
        String reClientAddr = "(\\S+)";
        String reDoubleDashed = "-\\s+-";
        String reTimeStamp = "\\[(.+)\\]";
        String reHTTPRequest = "[\"“](\\S+)\\s(\\S+)(?:\\s+(\\S+))?[\"”]";
        String reHTTPResponse = "(\\d+)\\s([\\d-]+)";
        String assembled = "^\\s*" + reClientAddr +
            "\\s*" + reDoubleDashed + 
            "\\s*" + reTimeStamp + 
            "\\s*" + reHTTPRequest + 
            "\\s*" + reHTTPResponse +
            "\\s*$";
        REGEX_PATTERN = Pattern.compile(assembled);
    }

    static {
        buildRegexPattern();
    }
}
