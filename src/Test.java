import java.util.*;
public class Test {
    public static void main(String[] args) {
        String path = "www.google.com";
        String[] s = path.split("\\.");
        ArrayList<String> list = new ArrayList<String>();
        for (String ss : s) {
            if (ss.length() == 0) {
                continue;
            }
            list.add(ss);
            System.out.println(ss);
        }
        System.out.println(s.length);
        System.out.println(list.size());
    }
}
