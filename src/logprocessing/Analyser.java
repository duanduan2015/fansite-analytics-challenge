package logprocessing;
import tools.*;
import java.io.*;

public interface Analyser {
    public void analyze(LogEntry entry);
    public void reportResults() throws IOException;
}
