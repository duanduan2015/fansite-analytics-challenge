import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Test {
    public static void main(String[] args){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
        String dateString = "01/Jul/1995:00:00:01";
        try {
            Date date = sdf.parse(dateString);
            System.out.println(date);
        } catch (Exception e) {

        }
    }
}
