import java.util.*;
public class IPv4Address implements Comparable<IPv4Address> {
    private Integer address;
    public IPv4Address (String s) {
        String[] parts = s.split("\\.");
        for (int i = 0; i < parts.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(parts[i]);
            this.address += ip * Math.pow(256, power);
        }
    }

    public Integer getAddress() {
        return this.address;
    }
    
    public int compareTo(IPv4Address ip) {
        return this.address - ip.getAddress();
    }

}

