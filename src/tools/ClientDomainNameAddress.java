package tools;
import java.util.*;

/**
 * ClientDomainNameAddress implements a DomainName
 * to represnet the clients' domain name address.
 * @author Yunduan Han
 */
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
