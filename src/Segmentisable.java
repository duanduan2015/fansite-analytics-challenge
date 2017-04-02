import java.util.*;
public interface Segmentisable {
    public List<String> getSegments();
    public int segmentsSize();
    public String toString();
    public Segmentisable nextSubSegments();
    public boolean isLastLayer();
    public String getTopLayerName();
}
