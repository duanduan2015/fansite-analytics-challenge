import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LogParser {

    private BufferedReader bufferedReader;
    private static Pattern REGEX_PATTERN;

    public LogParser(String path) throws IOException {
        bufferedReader = new BufferedReader(new FileReader(new File(path)));
    }

    public void parse() throws IOException {
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            Matcher m = REGEX_PATTERN.matcher(line);
            if (m.find()) {
                System.out.printf("%s,%s,%s,%s,%s,%s,%s\n", m.group(1), 
                        m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7));
            } else {
                throw new IllegalArgumentException("Unable to parse line '" + line + "'");
            }
        }
        bufferedReader.close();
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
