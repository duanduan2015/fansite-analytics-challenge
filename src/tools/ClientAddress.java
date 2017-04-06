package tools;

/**
 * ClientAddress is an interface 
 * for general client address.
 *
 * @author Yunduan Han
 */
public interface ClientAddress {
    public String toString();
    public boolean isIPAddress();
    public boolean isDomainAddress();
}

