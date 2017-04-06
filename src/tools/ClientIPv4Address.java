package tools;

/**
 * ClientIPv4Address implements an IPv4 address
 * for clients' IP address.
 * @author Yunduan Han
 */
public class ClientIPv4Address extends IPv4Address implements ClientAddress {

    private String addressString;
    private boolean isIPAddress;
    private boolean isDomainAddress;

    public ClientIPv4Address(String address) {
        super(address);
        this.addressString = address;
        this.isIPAddress = true;
        this.isDomainAddress = false;
    }

    public Integer getAddress() {
        return this.getIPValue();
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

