import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Test {
    public static void main(String[] args){
        String path1 = "home.duanduan.Documents.ComputerScience.html";
        ClientAddress r1 = new ClientDomainNameAddress(path1);
        String path2 = "home.duanduan.Documents.ComputerScience.xml";
        ClientAddress r2 = new ClientDomainNameAddress(path2);
        String path3 = "home.duanduan.Documents.html";
        ClientAddress r3 = new ClientDomainNameAddress(path3);
        String path4 = "home.duanduan.Documents.xml";
        ClientAddress r4 = new ClientDomainNameAddress(path4);
        String path5 = "home.hengyang.Documents.html";
        ClientAddress r5 = new ClientDomainNameAddress(path5);
        String path6 = "home.hengyang.Documents.xml";
        ClientAddress r6 = new ClientDomainNameAddress(path6);
        //PrefixTree<ClientDomainNameAddress, Integer> pt = new PrefixTree<ClientDomainNameAddress, Integer>();
        ClientAddressMap pt = new ClientAddressMap();
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
        System.out.println(pt.get(r66));
        //pt.getDomainTreeMap().printTree();
        String path7 = "hengyang.Documents.xml";
        ClientDomainNameAddress r7 = new ClientDomainNameAddress(path7);
        System.out.println(pt.get(r6));
        System.out.println(pt.get(r7));
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
