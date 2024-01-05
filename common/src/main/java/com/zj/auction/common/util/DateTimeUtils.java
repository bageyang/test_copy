package com.zj.auction.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Mao Qi
 * @title DateTimeUtils
 * @package com.duoqio.boot.util
 * @describe LocalDateTime
 * @date 2019/8/29 17:32
 * @copyright 重庆多企源科技有限公司
 * @website {http://www.duoqio.com/index.asp?source=code}
 */
public class DateTimeUtils {
    private final static String defaultPattern = "yyyy-MM-dd HH:mm:ss";
    private final static String defaultPattern2 = "yyyy-MM-dd HH:mm:ss.S";
    private final static DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(defaultPattern);

    /**
     * 获取当前系统日期时间
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * @param text 时间
     * @return java.time.LocalDateTime
     * @describe 时间转换 yyyy-MM-dd HH:mm:ss
     * @title parse
     * @author Mao Qi
     * @date 2019/8/29 17:44
     */
    public static LocalDateTime parse(final CharSequence text) {
        return parse(text, defaultFormatter);
    }

    public static LocalDateTime parse2(final CharSequence text) {
        return parse(text, defaultPattern2);
    }

    public static LocalDateTime parse3(final CharSequence text) {
        return parse(text, defaultPattern2);
    }

    /**
     * @param text 时间
     * @return java.time.LocalDateTime
     * @describe 时间转换 yyyy-MM-dd HH:mm:ss
     * @title parse
     * @author Mao Qi
     * @date 2019/8/29 17:44
     */
    public static LocalDateTime parse(final CharSequence text, final String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return parse(text, formatter);
    }

    /**
     * @param text 时间
     * @return java.time.LocalDateTime
     * @describe 时间转换 yyyy-MM-dd HH:mm:ss
     * @title parse
     * @author Mao Qi
     * @date 2019/8/29 17:44
     */
    public static LocalDateTime parse(CharSequence text, DateTimeFormatter formatter) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        try {
            return LocalDateTime.parse(text, formatter);
        } catch (DateTimeParseException ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    /**
     * 当前时间字符串 yyyy-MM-dd HH:mm:ss
     *
     * @return java.lang.String
     * @title toString
     * @author Mao Qi
     * @date 2019/11/8 11:29
     */
    public static String nowString() {
        return toString(now(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String LocalDateTimeToString(LocalDateTime data) {
        return toString(data, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 当前时间字符串
     *
     * @param pattern 时间转换 yyyy-MM-dd HH:mm:ss
     * @return java.lang.String
     * @title toString
     * @author Mao Qi
     * @date 2019/11/8 11:29
     */
    public static String toString(LocalDateTime time, final String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return time.format(formatter);
    }


    /**
     * @Description 计算两个时间相差的毫秒
     * @Title toString
     * @Author Mao Qi
     * @Date 2020/6/16 20:00
     * @param startTime
     * @param entTime
     * @return	java.lang.String
     */
    public static Long timeApart(LocalDateTime startTime, LocalDateTime entTime) {
        System.out.println("计算两个时间的差");
        Duration duration = Duration.between(startTime,entTime);
        long millis = duration.toMillis();//相差毫秒数
        return millis;
    }


    /**
     * @Description 毫秒转化
     * @Title formatTime
     * @Author Mao Qi
     * @Date 2020/6/16 21:03
     * @param ms
     * @return	java.lang.String
     */
    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strMinute + "分" + strSecond + "秒";
    }

    /**
     * @Description date  转 LocalDateTime
     * @Title UDateToLocalDateTime
     * @Author Mao Qi
     * @Date 2020/6/18 9:35
     * @param date
     * @return	java.time.LocalDateTime
     */
    public static LocalDateTime UDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    //获取当前月份日期
    public static void getMonthDay(List<String> dateList) {
        Calendar cd = Calendar.getInstance();
        int year = cd.get(Calendar.YEAR);
        int month = cd.get(Calendar.MONTH);
        int day = 0;

        int[] monDays = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ( ( (year) % 4 == 0 && (year) % 100 != 0) ||(year) % 400 == 0) {
            day = monDays[month]++;
        } else {
            day = monDays[month];
        }
        System.out.println(day);
        int monthNum = month*1 + 1*1;
        String monthStr = ""+monthNum;
        if (monthNum < 10){
            monthStr = "0"+monthNum;
        }

        for (int i = 1; i <= day; i++) {
            String iStr = ""+i;
            if (i < 10){
                iStr = "0"+i;
            }
            String date = year+"-"+monthStr+"-"+iStr;
            dateList.add(date);
        }
    }

    //获取指定月份日期
    public static void getTimeDate(String startTime, List<String> dateList) throws ParseException {
        //截取年份，月份
        Date date = new SimpleDateFormat("yyyy-MM").parse(startTime);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int month = ca.get(Calendar.MONTH);//第几个月
        int year = ca.get(Calendar.YEAR);//年份数值
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int day = a.get(Calendar.DATE);
        int monthNum = month*1 + 1*1;
        String monthStr = ""+monthNum;
        if (monthNum < 10){
            monthStr = "0"+monthNum;
        }

        for (int i = 1; i <= day; i++) {
            String iStr = ""+i;
            if (i < 10){
                iStr = "0"+i;
            }
            String dates = year+"-"+monthStr+"-"+iStr;
            dateList.add(dates);
        }
    }

}
