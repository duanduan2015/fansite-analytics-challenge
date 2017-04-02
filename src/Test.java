import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Test {
    public static void main(String[] args){
        String path1 = "/home/duanduan/Documents/ComputerScience/html";
        ResourcePath r1 = new ResourcePath(path1);
        String path2 = "/home/duanduan/Documents/ComputerScience/xml";
        ResourcePath r2 = new ResourcePath(path2);
        String path3 = "/home/duanduan/Documents/html";
        ResourcePath r3 = new ResourcePath(path3);
        String path4 = "/home/duanduan/Documents/xml";
        ResourcePath r4 = new ResourcePath(path4);
        String path5 = "/home/hengyang/Documents/html";
        ResourcePath r5 = new ResourcePath(path5);
        String path6 = "/home/hengyang/Documents/xml";
        ResourcePath r6 = new ResourcePath(path6);
        PrefixTree<ResourcePath, Integer> pt = new PrefixTree<ResourcePath, Integer>();
        pt.put(r1, 1);
        pt.put(r2, 2);
        pt.put(r3, 3);
        pt.put(r4, 4);
        pt.put(r5, 5);
        pt.put(r6, 6);
        pt.printTree();
        String path7 = "/hengyang/Documents/xml";
        ResourcePath r7 = new ResourcePath(path7);
        System.out.println(pt.contains(r7)); //false;
        System.out.println(pt.contains(r6)); //true;
        pt.put(r6, 66);
        System.out.println(pt.get(r6));
        pt.printTree();
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
