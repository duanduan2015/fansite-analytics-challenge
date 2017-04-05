package tools;
import java.util.*;

public class ClientAddressMap {
    private TreeMap<Integer, Integer> ipTreeMap;
    private PrefixTree<ClientDomainNameAddress, Integer> domainTreeMap;

    public ClientAddressMap() {
        this.ipTreeMap = new TreeMap<Integer, Integer>();
        this.domainTreeMap = new PrefixTree<ClientDomainNameAddress, Integer>();
    }

    public boolean contains(ClientAddress address) {
        if (address.isIPAddress()) {
            ClientIPv4Address ad = (ClientIPv4Address) address;
            return this.ipTreeMap.containsKey(ad.getAddress());
        }
        if (address.isDomainAddress()) {
            ClientDomainNameAddress ad = (ClientDomainNameAddress) address;
            return this.domainTreeMap.contains(ad);
        }
        return false;
    }

    public void put(ClientAddress address, Integer value) {
        if (address.isIPAddress()) {
            ClientIPv4Address ad = (ClientIPv4Address) address;
            ipTreeMap.put(ad.getAddress(), value);
        }
        if (address.isDomainAddress()) {
            ClientDomainNameAddress ad = (ClientDomainNameAddress) address;
            domainTreeMap.put(ad, value);
        }
    }

    public Integer get(ClientAddress address) {
        if (address.isIPAddress()) {
            ClientIPv4Address ad = (ClientIPv4Address) address;
            if (!ipTreeMap.containsKey(ad.getAddress())) {
                return null;
            } else {
                return ipTreeMap.get(ad.getAddress());
            }
        }
        if (address.isDomainAddress()) {
            ClientDomainNameAddress ad = (ClientDomainNameAddress) address;
            if (!domainTreeMap.contains(ad)) {
                return null;
            } else {
                return domainTreeMap.get(ad);
            }
        }
        return null; 
    }

    public PrefixTree<ClientDomainNameAddress, Integer> getDomainTreeMap() {
        return this.domainTreeMap;
    }

    public TreeMap<Integer, Integer> getIPTreeMap() {
        return this.ipTreeMap;
    }

}
