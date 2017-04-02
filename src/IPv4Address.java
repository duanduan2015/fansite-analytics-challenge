import java.util.*;
public class IPv4Address implements Comparable<IPv4Address> {
    private Integer integerAddress;
    public IPv4Address (String s) {
        String[] parts = s.split("\\.");
        this.integerAddress = 0;
        for (int i = 0; i < parts.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(parts[i]);
            this.integerAddress += ip * (int)(Math.pow(256, power));
        }
    }

    public Integer getAddress() {
        return this.integerAddress;
    }
    
    public int compareTo(IPv4Address ip) {
        return this.integerAddress - ip.getAddress();
    }

}

