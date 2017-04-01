import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputPath = args[0];
        BufferedReader br = new BufferedReader(new FileReader(new File(inputPath)));
        String line = br.readLine();
        String[] ss = line.split(" ");
        for (String s : ss) {
            System.out.println(s);
        }
        String s = "/home/dis/duanduan";
        ss = s.split("/");
        for (String sss : ss) {
            System.out.println(sss);
        }

        System.out.println(ss[0].matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"));
        
        /*while ((line = br.readLine()) != null) {
            System.out.println(line);
        }*/
        br.close();
    }
}
