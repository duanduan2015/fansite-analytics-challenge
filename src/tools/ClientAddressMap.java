package tools;
import java.util.*;

/**
 * ClientAddressMap manage a general map of 
 * client addresses, it includes a TreeMap for 
 * IP address and a PrefixTree map for domain
 * name address.
 *
 * @author Yunduan Han
 */
public class ClientAddressMap {
    private TreeMap<Integer, Integer> ipTreeMap;
    private PrefixTree<ClientDomainNameAddress, Integer> domainTreeMap;

    public ClientAddressMap() {
        this.ipTreeMap = new TreeMap<Integer, Integer>();
        this.domainTreeMap = new PrefixTree<ClientDomainNameAddress, Integer>();
    }

    /**
     * Determine whether a cliendAddress is in this map.
     */
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

    /**
     * Put a (key, value) pair in this map.
     */
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

    /**
     * Get a value according to a address key.
     */
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

    /**
     * Return the PrefixTree in this map.
     */
    public PrefixTree<ClientDomainNameAddress, Integer> getDomainTreeMap() {
        return this.domainTreeMap;
    }

    /**
     * Return the TreeMap in this map
     */
    public TreeMap<Integer, Integer> getIPTreeMap() {
        return this.ipTreeMap;
    }

}
