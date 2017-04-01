import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
        String inputPath = args[0];
        LogParser parser = new LogParser(inputPath);
    }
}
