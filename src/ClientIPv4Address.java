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
        return this.integerAddress;
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

