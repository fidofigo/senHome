package com.senhome.shell.common.time;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User:  希欧
 * Date: 14-6-29
 * Time: 上午11:17
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {

    public static Date getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.set(year, month, day, hour, 0, 0);

        long time = calendar.getTimeInMillis();
        time = time / 1000 * 1000;

        return new Date(time);
    }

    /**
     * 获得第二天
     *
     * @param date 该日期是第二天0点
     * @return
     * @throws Exception
     */
    public static Date getNextDay(Date date) throws Exception {
        long time = date.getTime();
        time += 24L * 60 * 60 * 1000;

        Date nexDay = new Date(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nexDay);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, month, day, 0, 0, 0);

        time = calendar.getTimeInMillis();
        time = time / 1000 * 1000;

        return new Date(time);
    }

    public static Date get24HBeforeDay(Date date) {
        long time = date.getTime();
        time -= 24L * 60 * 60 * 1000;
        return new Date(time);
    }

    public static Date getToday() {
        Date myDate = new Date();

        return getToday(myDate);
    }

    public static Date get24HAfterDay(Date date) {
        long time = date.getTime();
        time += 24L * 60 * 60 * 1000;
        return new Date(time);
    }

    public static int getDaysTo1970(Date date) {
        long millisecond = 24L * 60 * 60 * 1000;
        return (int) (date.getTime() / millisecond);
    }

    public static Date getToday(Date myDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, month, day, 0, 0, 0);

        long time = calendar.getTimeInMillis();
        time = time / 1000 * 1000;

        return new Date(time);
    }

    //获得上个小时的整点
    public static Date getHourBefore() {
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.set(year, month, day, hour, 0, 0);

        calendar.add(Calendar.HOUR_OF_DAY, -1);

        long time = calendar.getTimeInMillis();
        time = time / 1000 * 1000;

        return new Date(time);
    }

    public static Date getMonth(Date myDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, month, 1, 0, 0, 0);

        long time = calendar.getTimeInMillis();
        time = time / 1000 * 1000;

        return new Date(time);
    }

    public static Date getYesterday() {
        Date today = getToday();
        return get24HBeforeDay(today);
    }

    public static Date getDayBeforeYesterday() {
        Date yesterday = getYesterday();
        return get24HBeforeDay(yesterday);
    }

    public static int getMonth() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.MONTH);

    }

    public static int getDaysBetween(Date from, Date to) {

        LocalDate fromDate = LocalDate.fromDateFields(from);
        LocalDate toDate = LocalDate.fromDateFields(to);
        return Days.daysBetween(fromDate, toDate).getDays();

    }


    public static String now() {

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return DateTime.now().toString(formatter);

    }

    public static String formatYmdHmsString(Date date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatter);

    }

    public static String formatYmdHmString(Date date) {
        if (date == null) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatter);
    }

    public static String formatYmdHString(Date date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH");
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatter);

    }

    public static String formatYmdString(Date date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatter);

    }

    public static String formatYmdString2(Date date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy.MM.dd");
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatter);

    }

    public static String formatYmdChineseString(Date date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy年MM月dd日");
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatter);

    }

    public static Date getYmdDate(String dateString) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatDate.parse(dateString);
        } catch (Exception e) {
        }
        return date;
    }

    public static Date getYmdHmsDate(String dateString) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatDate.parse(dateString);
        } catch (Exception e) {
        }
        return date;
    }

    public static Date getYmdHmDate(String dateString) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = formatDate.parse(dateString);
        } catch (Exception e) {
        }
        return date;
    }

    public static Date getYmdHDate(String dateString) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH");
        Date date = null;
        try {
            date = formatDate.parse(dateString);
        } catch (Exception e) {
        }
        return date;
    }

    public static String formatString(Date date, String format) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatter);

    }

    public static String formatBetweenDate(Date startDate, Date endDate) {

        DateTime from = new DateTime(startDate);
        DateTime to = new DateTime(endDate);
        int days = Days.daysBetween(from, to).getDays();
        String format = "";
        if (days > 0) {
            from = from.plusDays(days);
            format += days + "天";
        }
        from.plusDays(days);
        int hours = Hours.hoursBetween(from, to).getHours();
        if (hours > 0) {
            from = from.plusHours(hours);
            format += hours + "小时";
        }
        from.plusHours(hours);
        int minutes = Minutes.minutesBetween(from, to).getMinutes();
        if (minutes > 0) {
            format += minutes + "分钟";
        }
        from = from.plusMinutes(minutes);
        int seconds = Seconds.secondsBetween(from, to).getSeconds();
        if (seconds > 0) {
            format += seconds + "秒";
        }
        return format;

    }

    public static String formatBetweenDate2(Date startDate, Date endDate) {

        DateTime from = new DateTime(startDate);
        DateTime to = new DateTime(endDate);
        int days = Days.daysBetween(from, to).getDays();
        String format = "";
        if (days > 0) {
            from = from.plusDays(days);
            format += days + "天";
        }
        from.plusDays(days);
        int hours = Hours.hoursBetween(from, to).getHours();
        if (hours > 0) {
            from = from.plusHours(hours);
            format += hours + "小时";
        }
        from.plusHours(hours);
        int minutes = Minutes.minutesBetween(from, to).getMinutes();
        if (minutes > 0) {
            format += minutes + "分钟";
        }
        return format;

    }

    public static int lastDays(Date startDate, Date endDate) {
        DateTime from = new DateTime(startDate);
        DateTime to = new DateTime(endDate);
        int days = Days.daysBetween(from, to).getDays();
        if (days < 0) {
            days = 0;
        }
        return days;
    }

    public static int lastHours(Date startDate, Date endDate) {
        DateTime from = new DateTime(startDate);
        DateTime to = new DateTime(endDate);
        int days = Days.daysBetween(from, to).getDays();
        if (days > 0) {
            from = from.plusDays(days);
        }
        int hours = Hours.hoursBetween(from, to).getHours();
        if (hours < 0) {
            hours = 0;
        }
        return hours;
    }

    public static int lastMinutes(Date startDate, Date endDate) {
        DateTime from = new DateTime(startDate);
        DateTime to = new DateTime(endDate);
        int days = Days.daysBetween(from, to).getDays();
        if (days > 0) {
            from = from.plusDays(days);
        }
        int hours = Hours.hoursBetween(from, to).getHours();
        if (hours > 0) {
            from = from.plusHours(hours);
        }
        int minutes = Minutes.minutesBetween(from, to).getMinutes();
        if (minutes < 0) {
            minutes = 0;
        }
        return minutes;
    }

    public static int lastSeconds(Date startDate, Date endDate) {
        DateTime from = new DateTime(startDate);
        DateTime to = new DateTime(endDate);
        int days = Days.daysBetween(from, to).getDays();
        if (days > 0) {
            from = from.plusDays(days);
        }
        int hours = Hours.hoursBetween(from, to).getHours();
        if (hours > 0) {
            from = from.plusHours(hours);
        }
        int minutes = Minutes.minutesBetween(from, to).getMinutes();
        if (minutes > 0) {
            from = from.plusMinutes(minutes);
        }
        int seconds = Seconds.secondsBetween(from, to).getSeconds();
        if (seconds < 0) {
            seconds = 0;
        }
        return seconds;
    }

    /**
     * 返回二位的时间如"09",在个位数时间前补零
     *
     * @param t
     * @return
     */
    public static String getFormatTime(int t) {
        DecimalFormat df = new DecimalFormat("00");
        return df.format(t);
    }

    /**
     * <p>
     * 时间做加法
     * </p>
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date plusMinutes(Date date, int minutes) {

        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusMinutes(minutes);
        return dateTime.toDate();

    }

    /**
     * <p>
     * 时间做加法
     * </p>
     *
     * @param date
     * @param hours
     * @return
     */
    public static Date plusHours(Date date, int hours) {

        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusHours(hours);
        return dateTime.toDate();

    }

    /**
     * <p>
     * 时间做加法
     * </p>
     *
     * @param date
     * @param days
     * @return
     */
    public static Date plusDays(Date date, int days) {

        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusDays(days);
        return dateTime.toDate();

    }
    
    /**
     * <p>
     * 时间做减法
     * </p>
     *
     * @param date
     * @param days
     * @return
     */
    public static Date minusDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.minusDays(days);
        return dateTime.toDate();
    }
    

    /**
     * 3天前的时间
     *
     * @return
     */
    public static Date getThreeDateBeforeNow() {
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 3);
        return calendar.getTime();
    }

    /**
     * 获得当天的某个小时的整点DATE
     *
     * @param hour
     * @return
     */
    public static Date getFloorHourBefore(int hour) {
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, month, day, hour, 0, 0);

        long time = calendar.getTimeInMillis();
        time = time / 1000 * 1000;

        return new Date(time);
    }

    public static boolean inToday(Date date) {
        long today = getToday().getTime();
        long tomorrow = get24HAfterDay(getToday()).getTime();

        long dateTime = date.getTime();
        return dateTime >= today && dateTime < tomorrow;
    }

    /**
     * 判断一个时间是否处在一个时间段内
     * @param time 时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public static boolean isBetweenTime(Date time, Date startTime, Date endTime) {
        if (null == startTime || null == endTime)
        {
            return false;
        }
        if (time.before(startTime) || time.after(endTime)) {
            return false;
        }
        return true;
    }

    /**
     * 将字符串转换为date格式（默认个是为：yyyy-MM-dd）
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parseDate(String dateStr, String pattern) {
        if (StringUtils.isEmpty(dateStr))
        {
            return null;
        }
        if (StringUtils.isEmpty(pattern))
        {
            pattern = "yyyy-MM-dd";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(get24HAfterDay(getToday()));
        System.out.println(getToday());
    }
}
