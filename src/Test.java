import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Test {
    public static void main(String[] args){
        String path1 = "home/duanduan/Documents/ComputerScience/html";
        ResourcePath r1 = new ResourcePath(path1);
        String path2 = "home/duanduan/Documents/ComputerScience/xml";
        ResourcePath r2 = new ResourcePath(path2);
        String path3 = "home/duanduan/Documents/html";
        ResourcePath r3 = new ResourcePath(path3);
        String path4 = "home/duanduan/Documents/xml";
        ResourcePath r4 = new ResourcePath(path4);
        String path5 = "home/hengyang/Documents/html";
        ResourcePath r5 = new ResourcePath(path5);
        String path6 = "home/hengyang/Documents/xml";
        ResourcePath r6 = new ResourcePath(path6);
        ResourcePathMap rm = new ResourcePathMap();
        rm.put(r1, 1);
        rm.put(r2, 2);
        rm.put(r3, 3);
        rm.put(r4, 4);
        rm.put(r5, 5);
        rm.put(r6, 6);
        rm.getResourceTree().printTree();
        /*ClientAddressMap pt = new ClientAddressMap();
        ClientAddress r11 = new ClientIPv4Address("192.163.1.1");
        ClientAddress r22 = new ClientIPv4Address("192.163.1.2");
        ClientAddress r33 = new ClientIPv4Address("192.163.1.3");
        ClientAddress r44 = new ClientIPv4Address("192.163.1.4");
        ClientAddress r55 = new ClientIPv4Address("192.163.1.5");
        ClientAddress r66 = new ClientIPv4Address("192.163.1.6");
        ClientAddress r77 = new ClientIPv4Address("192.163.1.7");
        pt.put(r11, 1);
        pt.put(r22, 2);
        pt.put(r33, 3);
        pt.put(r44, 4);
        pt.put(r55, 5);
        pt.put(r66, 6);
        System.out.println(pt.contains(r77)); //false;
        System.out.println(pt.contains(r66)); //true;
        System.out.println(pt.get(r55));
        System.out.println(pt.get(r66));*/
        //pt.getDomainTreeMap().printTree();
        //pt.printTree();
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