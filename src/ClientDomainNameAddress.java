public class ClientDomainNameAddress extends DomainName implements ClientAddress {

    private String addressString;

    public ClientDomainNameAddress(String s) {
        super(s);
        this.addressString = s;
    }

    public String toString() {
        return this.addressString;
    }
}
