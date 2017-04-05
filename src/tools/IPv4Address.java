package tools;
import java.util.*;

public class IPv4Address implements Comparable<IPv4Address> {
    
    private Integer integerAddress;
    public IPv4Address (String s) {
        String[] parts = s.split("\\.");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Bad IPv4 address " + s);
        }

        int n = 0;
        for (int i = 0; i < parts.length; i++) {
            int ip = Integer.parseInt(parts[i]);
            if ((ip | 0xff) != 0xff) {
                throw new IllegalArgumentException("Bad IPv4 Address " + s);
            }
            n <<= 8;
            n |= ip; 
        }

        this.integerAddress = n;
    }
    
    @Override
    public int compareTo(IPv4Address ip) {
        return this.integerAddress - ip.getIPValue();
    }

    public Integer getIPValue() {
        return this.integerAddress;
    }

    public String toString() {
        String result = "";
        for (int i = 3; i >= 0; --i) {
            result = result + Integer.toString((this.integerAddress >>> i * 8) & 0xff);
            if (i <= 0) continue;
            result = result + ".";
        }
        return result;
    }
}

