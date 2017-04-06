package logprocessing;
import tools.*;
import java.io.*;

/**
* The Analyser interface used for four specific analyser
* int this project, each analyser generates one feature.
*
* @author Yunduan Han 
*/
public interface Analyser {

    /**
     * Analyze the entry to get a result.
     * @param entry One LogEntry object.
     */
    public void analyze(LogEntry entry);

    /**
     * Report the result to specified output.
     */
    public void reportResults() throws IOException;
}
