package logprocessing;
import tools.*;
import java.io.*;

/**
* The Analyser interface used for four specific analysers
* int this project, each analyser compute one feature's result.
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
