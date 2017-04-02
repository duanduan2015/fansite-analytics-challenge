import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Test {
    public static void main(String[] args){
        String path = "/home/duanduan/Documents/ComputerScience/html";
        ResourcePath r = new ResourcePath(path);
        PrefixTree<ResourcePath, Integer> pt = new PrefixTree<ResourcePath, Integer>();
        System.out.println(pt.contains(r));
        pt.put(r, 1);
        System.out.println(pt.contains(r));
        System.out.println(pt.get(r));
        /*String key = "key";
        Integer value = new Integer(1);
        PrefixTree<String, Integer> tree = new PrefixTree<String, Integer>(key, value);
        System.out.println(tree.getKey());
        System.out.println(tree.getValue());*/
        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
        String dateString = "01/Jul/1995:00:00:01";
        try {
            Date date = sdf.parse(dateString);
            System.out.println(date);
        } catch (Exception e) {

        }*/
    }
}
