package emlyn.ma.my.java.base.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTransferError {

    public static void main(String[] args) throws ParseException {
        String dateStr = "0000-00-00 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        System.out.println(sdf.format(date));
        System.out.println(date.getTime());




        String defaultTime = "1971-01-01 00:00:00";
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(defaultTime);
        System.out.println(sdf.format(date));

        System.out.println(date.getTime());

        date = new Date();
        date.setTime(31507200000L);
        System.out.println(date);
        System.out.println(sdf.format(date));

    }

}
