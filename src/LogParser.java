import java.io.*;
public class LogParser {
   public LogParser(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
   }
}
