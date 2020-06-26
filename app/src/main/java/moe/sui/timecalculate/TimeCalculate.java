package moe.sui.timecalculate;

import java.util.Date;
import java.util.Locale;

public class TimeCalculate {

    /**
     * 将Date输出为"yyyy-mm-dd HH:MM:SS"形式的String
     */
    public static String dateToString(Date date){
        return String.format(Locale.CHINA,"%tF %tT",date,date);
    }

    /**
     * 输入一个Date，和一个int minute，返回minute分钟后的Date转换的String
     */
    public static String dateAfterMinute(Date date,int minute){
        long timestamp=date.getTime();

        long newtimestamp = timestamp+ minute * 60 * 1000;

        Date newDate = new Date(newtimestamp);
        return dateToString(newDate);
    }
}
