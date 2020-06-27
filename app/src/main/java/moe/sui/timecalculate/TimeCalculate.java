package moe.sui.timecalculate;

import android.icu.util.ULocale;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeCalculate {

    /**
     * 将Date输出为"yyyy-mm-dd HH:MM:SS"形式的String
     */
    public static String dateToString(Date date){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return df.format(date); //时间转字符串
    }

    /**
     * 将Date输出为"HH:MM:SS"形式的String
     *
     *
     */
    public static String dateToStringHMS(Date date){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return df.format(date); //时间转字符串
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
