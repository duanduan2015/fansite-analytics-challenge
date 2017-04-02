public class ClientIPv4Address extends IPv4Address implements ClientAddress {
    private String addressString;
    public ClientIPv4Address(String address) {
        super(address);
        this.addressString = address;
    }

    public String toString() {
        return this.addressString;
    }
}

