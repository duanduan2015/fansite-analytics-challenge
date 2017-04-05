package tools;
import java.util.*;
public class ClientDomainNameAddress extends DomainName implements ClientAddress {

    private String addressString;
    private boolean isIPAddress;
    private boolean isDomainAddress;

    public ClientDomainNameAddress(String s) {
        super(s);
        this.addressString = s;
        this.isIPAddress = false;
        this.isDomainAddress = true;
    }

    public String toString() {
        return this.addressString;
    }

    public boolean isIPAddress() {
        return this.isIPAddress;
    }

    public boolean isDomainAddress() {
        return this.isDomainAddress;
    }

}
