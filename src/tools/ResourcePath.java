package tools;
import java.util.*;

/**
 * ResourcePath parses a string
 * to generate a layer name list
 * of this resource address.
 *
 * @author Yunduan Han
 */
public class ResourcePath implements Segmentisable {

    private List<String> segments;
	private static final char DELIMITER = '/';

    public ResourcePath(String s) {
		this.segments = new ArrayList<String>();
		char[] charArray = s.trim().toCharArray();
		int offset = 0;
		for (int i = 0; i < charArray.length; ++i) {
			if (charArray[i] == DELIMITER) {
				if (i > offset) {
					this.segments.add(new String(charArray, offset, i - offset));
				}
				offset = i + 1;
			}
		}
		if (offset != charArray.length) {
			this.segments.add(new String(charArray, offset, charArray.length - offset));
		} else {
			if (this.segments.size() > 0) {
				this.segments.add("");
			}
		}
    }

    public List<String> getSegments() {
        return this.segments;
    }

    public String toString() {
        if (this.segments.size() == 0) {
            return "/";
        }
        StringBuilder path = new StringBuilder("/");
        for (int i = 0; i < this.segments.size() - 1; i++) {
            path.append(this.segments.get(i));
            path.append("/");
        }
        path.append(this.segments.get(this.segments.size() - 1));
        return path.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourcePath)) {
            return false;
        }
        ResourcePath path = (ResourcePath) o;
        return this.segments.equals(path.getSegments());
    }
}
