package com.creatoo.hn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by Administrator on 2017/4/12.
 */
@SuppressWarnings("all")
public class DateUtils {
    public static final SimpleDateFormat LONGDATEFORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    public static String DEFAULT_FORMAT = "yyyy-MM-dd";
    /**
     * yyMMdd 140925
     */
    public static final String DATE_PATTERN_yyMMdd = "yyMMdd";

    /**
     * yyyyMMdd 20140518
     */
    public static String DATE_PATTERN_yyyyMMdd = "yyyyMMdd";

    /**
     * yyyy-MM-dd 2014-05-18
     */
    public static String DATE_PATTERN_yyyy_MM_dd = "yyyy-MM-dd";

    /**
     * yyyy-MM-dd HH:mm:ss 2014-05-18 20:20:20
     */
    public static String DATE_PATTERN_yyyy_MM_dd_HH_MM_ss = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm 2014-05-18 20:20
     */
    public static String DATE_PATTERN_yyyy_MM_dd_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * yyyy-MM-dd_HHmmss_SSS 2015-05-18_20-20-20_123
     */
    public static String DATE_PATTERN_yyyy_MM_dd_HHmmss_SSS = "yyyy-MM-dd_HHmmss_SSS";

    private static final DateFormat df = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private static final DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

    private static final DateFormat df3 = new SimpleDateFormat(
            "yyyyMMdd HH:mm:ss");

    private static final DateFormat df4 = new SimpleDateFormat("yyyyMMdd");

    private static final DateFormat df5 = new SimpleDateFormat(
            "yyyy.MM.dd HH:mm:ss");

    private static final DateFormat df6 = new SimpleDateFormat("yyyy.MM.dd");

    /**
     * 一天的开始时间点   00:00:00
     */
    public static String START_TIME = " 00:00:00";

    /**
     * 一天的结束时间点   23:59:59
     */
    public static String END_TIME = " 23:59:59";

    private DateUtils() {
    }

    /**
     * 格式化日期
     *
     * @param date 日期对象
     * @return String 日期字符串
     */
    public static String formatDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getCurrYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getCurrYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 获取某年的下一年的第一天
     *
     * @param year 年份
     * @return Date
     */
    public static Date getNextYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 获取当月第一天日期
     *
     * @return String
     */
    public static Date getCurrMonthFirst() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.parse(sdf.format(ca.getTime()));
    }

    /**
     * 获取下月第一天日期
     *
     * @return String
     */
    public static Date getNextMonthFirst() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.MONTH, 1);
        return sdf.parse(sdf.format(ca.getTime()));
    }

    /**
     * 获取当月的天数
     *
     * @return String
     */
    public static int getCountCurrMonthFirst() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DATE, -1);
        return ca.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当天开始日期时间
     *
     * @return
     * @throws Exception
     */
    public static Date getCurrentDateFirst() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar cal = Calendar.getInstance();
        return format.parse(format.format(cal.getTime()));
    }

    /**
     * 获取当天结束日期时间(下一天开始日期)
     *
     * @return
     * @throws Exception
     */
    public static Date getCurrentDateLast() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return format.parse(format.format(cal.getTime()));
    }


    public static Date calculateStartOfTomorrow(Date taskCrtDttm) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(taskCrtDttm);
        cal.add(Calendar.DATE, 1);
        return calculateStartOfDay(cal.getTime());
    }

    /**
     * 在传入时间上减一秒
     *
     * @param date 传入时间
     * @return 减去一秒的时间
     */
    public static Date cutOneSecond(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null!");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, -1);
        return cal.getTime();
    }

    /**
     * 在传入时间上增加一秒
     *
     * @param date 传入时间
     * @return 增加一秒的时间
     */
    public static Date addOneSecond(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null!");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, 1);
        return cal.getTime();
    }

    /**
     * 计算两个时间相差的月数
     *
     * @param before 开始时间
     * @param after  结束时间
     * @return 两个时间相差的月数
     */
    public static int calculateMonthCnt(Date before, Date after) {
        if (before == null) {
            throw new IllegalArgumentException("The before must not be null!");
        }

        if (after == null) {
            throw new IllegalArgumentException("The after must not be null!");
        }

        Calendar calBefore = Calendar.getInstance();
        calBefore.setTime(before);

        Calendar calAfter = Calendar.getInstance();
        calAfter.setTime(after);

        return (calAfter.get(Calendar.YEAR) - calBefore.get(Calendar.YEAR)) * 12
                + calAfter.get(Calendar.MONTH) - calBefore.get(Calendar.MONTH);
    }

    /**
     * 根据传入时间获得对应当天的开始时间
     *
     * @param taskCrtDttm
     * @return
     */
    public static Date calculateStartOfDay(Date taskCrtDttm) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_yyyy_MM_dd);
        try {
            String source = sdf.format(taskCrtDttm) + START_TIME;
            sdf.applyPattern(DATE_PATTERN_yyyy_MM_dd_HH_MM_ss);
            return sdf.parse(source);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 根据传入时间获得对应当天的结束时间
     *
     * @param taskCrtDttm
     * @return 如：taskCrtDttm=2014-08-23 11:55:23
     * 则 return 2014-08-23 23:59:59
     */
    public static Date calculateEndOfDay(Date taskCrtDttm) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_yyyy_MM_dd);
        try {

            String source = sdf.format(taskCrtDttm) + END_TIME;
            sdf.applyPattern(DATE_PATTERN_yyyy_MM_dd_HH_MM_ss);
            return sdf.parse(source);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取传入时间下一天的开始时间，即传入时间第二天的0点0分0秒0毫秒
     *
     * @param date 传入时间
     * @return 下一天的开始时间，例如：传入 2015-1-21 11:50:50 567 则返回 2015-1-22 00:00:00 000
     */
    public static Date getStartOfSecondDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 字符串转换为java.util.Date<br>
     * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z Example:'2002-1-1 AD at 22:10:59 PSD'<br>
     * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
     * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
     * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
     * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
     *
     * @param time String 字符串<br>
     * @return Date 日期<br>
     */
    public static Date getStringToDate(String time) {
        SimpleDateFormat formatter;
        int tempPos = time.indexOf("AD");
        time = time.trim();
        formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
        if (tempPos > -1) {
            time = time.substring(0, tempPos) + "公元" + time.substring(tempPos + "AD".length());// china
            formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
        }
        tempPos = time.indexOf("-");
        if (tempPos > -1 && (time.indexOf(" ") < 0)) {
            formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
        } else if ((time.indexOf("/") > -1) && (time.indexOf(" ") > -1)) {
            formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        } else if ((time.indexOf("-") > -1) && (time.indexOf(" ") > -1)) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if ((time.indexOf("/") > -1) && (time.indexOf("am") > -1) || (time.indexOf("pm") > -1)) {
            formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
        } else if ((time.indexOf("-") > -1) && (time.indexOf("am") > -1) || (time.indexOf("pm") > -1)) {
            formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
        }
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(time, pos);
        return date;
    }

    /**
     * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss'(24小时制)<br>
     * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11 17:24:21'<br>
     *
     * @param time Date 日期<br>
     * @return String 字符串<br>
     */
    public static String getDateToString(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Time = formatter.format(time);
        return Time;
    }

    /**
     * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss a'(12小时制)<br>
     * 如Sat May 11 17:23:22 CST 2002 to '2002-05-11 05:23:22 下午'<br>
     *
     * @param time Date 日期<br>
     * @param x    int 任意整数如：1<br>
     * @return String 字符串<br>
     */
    public static String getDateToString(Date time, int x) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
        String date = formatter.format(time);
        return date;
    }

    /**
     * 取系统当前时间:返回只值为如下形式 2002-10-30 20:24:39
     *
     * @return String
     */
    public static String getNow() {
        return getDateToString(new Date());
    }

    /**
     * 取系统当前时间:返回只值为如下形式 2002-10-30 08:28:56 下午
     *
     * @param hour 为任意整数
     * @return String
     */
    public static String getNow(int hour) {
        return getDateToString(new Date(), hour);
    }

    /**
     * 获取小时
     *
     * @return
     */
    public static String getHour() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("H");
        String hour = formatter.format(new Date());
        return hour;
    }

    /**
     * 获取当前日日期返回 <return>Day</return>
     */
    public static String getDay() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("d");
        String day = formatter.format(new Date());
        return day;
    }

    /**
     * 获取周
     *
     * @return
     */
    public static String getWeek() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("E");
        String week = formatter.format(new Date());
        return week;
    }

    /**
     * 获取月份
     *
     * @return
     */
    public static String getMonth() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("M");
        String month = formatter.format(new Date());
        return month;
    }

    /**
     * 获取年
     *
     * @return
     */
    public static String getYear() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy");
        String year = formatter.format(new Date());
        return year;
    }

    /**
     * 对日期格式的转换成("yyyy-MM-dd)格式的方法
     *
     * @param str
     * @return
     */
    public static java.sql.Date convert(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(str);
            java.sql.Date d1 = new java.sql.Date(d.getTime());
            return d1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前年、月、日：
     *
     * @return 格式“20140825”
     */
    public static String getYYMMDD() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//得到年
        int month = calendar.get(Calendar.MONTH) + 1;//得到月，因为从0开始的，所以要加1
        int day = calendar.get(Calendar.DAY_OF_MONTH);//得到天
        String monthString = month >= 10 ? month + "" : "0" + month;
        return year + monthString + day;
    }

    /**
     * 取系统当前时间:返回值为如下形式 2002-10-30
     *
     * @return String
     */
    public static String getYYYY_MM_DD() {
        return getDateToString(new Date()).substring(0, 10);
    }

    /**
     * 取系统给定时间:返回值为如下形式 2002-10-30
     *
     * @return String
     */
    public static String getYYYY_MM_DD(String date) {
        return date.substring(0, 10);
    }

    /**
     * 在jsp页面中的日期格式和sqlserver中的日期格式不一样，怎样统一?
     * 在页面上显示输出时，用下面的函数处理一下
     *
     * @param date
     * @return
     */
    public static String getFromateDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = formatter.format(date);
        return strDate;
    }

    /**
     * 获取当前时间是本年的第几周
     *
     * @return
     */
    public static String getWeeK_OF_Year() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return "当日是本年的第" + week + "周";
    }

    /**
     * 获取当日是本年的的第几天
     *
     * @return
     */
    public static String getDAY_OF_YEAR() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_YEAR);
        return "当日是本年的第" + day + "天";
    }

    /**
     * 获取本周是在本个月的第几周
     *
     * @return
     */
    public static String getDAY_OF_WEEK_IN_MONTH() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        /*
         * 这里这个值可以完全看JDK里面调用一下 或者点一下调用运行看看结果,看看里面的 English说明就知道它是干嘛的
		 */
        int week = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        return "本月第" + week + "周";
    }

    /**
     * 阳历转阴历农历:http://zuoming.iteye.com/blog/1554001
     * GregorianCalendar使用： http://zmx.iteye.com/blog/409465
     * GregorianCalendar 类提供处理日期的方法。
     * 一个有用的方法是add().使用add()方法，你能够增加象年
     * 月数，天数到日期对象中。要使用add()方法，你必须提供要增加的字段
     * 要增加的数量。一些有用的字段是DATE, MONTH, YEAR, 和 WEEK_OF_YEAR
     * 下面的程序使用add()方法计算未来80天的一个日期.
     * 在Jules的<环球80天>是一个重要的数字，使用这个程序可以计算
     * Phileas Fogg从出发的那一天1872年10月2日后80天的日期：
     */
    public static void getGregorianCalendarDate() {
        GregorianCalendar worldTour = new GregorianCalendar(1872, Calendar.OCTOBER, 2);
        worldTour.add(GregorianCalendar.DATE, 80);
        Date d = worldTour.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String s = df.format(d);
    }

    /**
     * 用来处理时间转换格式的方法
     *
     * @param formate
     * @param time
     * @return
     */
    private static String getConvertDateFormat(String formate, Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
        String date = dateFormat.format(time);
        return date;
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        // SimpleDateFormat simpleDateFormat = new
        // SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            c.setTime(date);
        }

        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            c.setTime(date);
        }
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    /**
     * 获得指定日期的前N天 的日期
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayByNum(String specifiedDay, int num) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            c.setTime(date);
        }

        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + num);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 得到本月的第一天
     *
     * @return
     */
    public static String getCurrentMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getConvertDateFormat("yyyy-MM-dd", calendar.getTime());
    }

    /**
     * 得到本月的最后一天
     *
     * @return
     */
    public static String getCurrentMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getConvertDateFormat("yyyy-MM-dd", calendar.getTime());
    }

    /**
     * 获取上个月的第一天
     *
     * @return
     */
    public static String getBeforeMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = 0;
        int month = cal.get(Calendar.MONTH); // 上个月月份
        int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH);// 起始天数

        if (month == 0) {
            year = cal.get(Calendar.YEAR) - 1;
            month = 12;
        } else {
            year = cal.get(Calendar.YEAR);
        }
        String endDay = year + "-" + month + "-" + day;
        return endDay;
    }

    /**
     * 获取上个月的最一天
     *
     * @return
     */
    public static String getBeforeMonthLastDay() {
        // 实例一个日历单例对象
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = 0;
        int month = cal.get(Calendar.MONTH); // 上个月月份
        // int day1 = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//起始天数
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数

        if (month == 0) {
            // year = cal.get(Calendar.YEAR) - 1;
            month = 12;
        } else {
            year = cal.get(Calendar.YEAR);
        }
        String endDay = year + "-" + month + "-" + day;
        return endDay;
    }

    /**
     * 获取下月的第一天
     *
     * @return
     */
    public static String getNextMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = 0;
        int month = cal.get(Calendar.MONDAY) + 2; // 下个月月份
        /*
         * 如果是这样的加一的话代表本月的第一天 int month = cal.get(Calendar.MONDAY)+1; int month
		 * = cal.get(Calendar.MONTH)+1
		 */
        int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH);// 起始天数

        if (month == 0) {
            year = cal.get(Calendar.YEAR) - 1;
            month = 12;
        } else {
            year = cal.get(Calendar.YEAR);
        }
        String Day = year + "-" + month + "-" + day;
        return Day;
    }

    /**
     * 获取下个月的最一天
     *
     * @return
     */
    public static String getNextMonthLastDay() {
        // 实例一个日历单例对象
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = 0;
        int month = cal.get(Calendar.MONDAY) + 2; // 下个月份
        // int day1 = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//起始天数
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数

        if (month == 0) {
            // year = cal.get(Calendar.YEAR) - 1;
            month = 12;
        } else {
            year = cal.get(Calendar.YEAR);
        }
        String endDay = year + "-" + month + "-" + day;
        return endDay;
    }

    /**
     * 本地时区输出当前日期 GMT时间
     */
    @SuppressWarnings("unchecked")
    public static String getLocalDate() {
        Date date = new Date();
        return date.toLocaleString();// date.toGMTString();
    }

    /**
     * 判断客户端输入的是闰年Leap还是平年Average
     *
     * @return
     */
    public static String getLeapOrAverage(int year) {

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return year + "闰年";
        } else {
            return year + "平年";
        }
    }

    /**
     * 数字不足位数左补0
     *
     * @param str 源字符串
     * @param len 目标长度
     */
    public static String padLeft(String str, int len) {
        int strLen = str.length();
        if (strLen < len) {
            while (strLen < len) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 当前yyyyMMdd+数字不足位数左补0
     *
     * @param str 源字符串
     * @param len 目标长度
     */
    public static String padLeftAddDate(String str, int len) {
        int strLen = str.length();
        if (strLen < len) {
            while (strLen < len) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        str = getYYMMDD() + str;
        return str;
    }

    /**
     * 数字不足位数右补0
     *
     * @param str 源字符串
     * @param len 目标长度
     */
    public static String padRight(String str, int len) {
        int strLen = str.length();
        if (strLen < len) {
            while (strLen < len) {
                StringBuffer sb = new StringBuffer();
                // sb.append("0").append(str);//左补0
                sb.append(str).append("0");// 右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 返回一个随机数
     *
     * @param i
     * @return
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        // int suiJiShu = jjj.nextInt(9);
        if (i == 0) {
            return "";
        }
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + String.valueOf(jjj.nextInt(9));
        }
        return jj;
    }

    /**
     * 返回一个 相差几个月的时间字符串
     *
     * @param date 时间
     * @param num  相差几个月
     * @return
     */
    public static String getDateByNumMonth(Date date, int num) {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        c.setTime(date);// 设置日历时间
        c.add(Calendar.MONTH, num);// 在日历的月份上增加6个月
        return sdf.format(c.getTime());
    }

    public static Date addMonth(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, num);

        return c.getTime();
    }

    public static String getOrderNo() {
        long No = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String nowdate = sdf.format(new Date());
        No = Long.parseLong(nowdate) * 1000;// 这里如果一天订单多的话可以用一万或更大
        return No + "";
    }

    //	public static Double getDayCount(Date begin){
    //
    //	}

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        //getYYMMDD();
        // int i = getMonths("2012-11-23", "2013-2-13");

        // 15));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -7);

    }

    /**
     * 一个日期加上多少分钟，算出之后的日期,返回的是String类型
     */
    public static String addMinutesToDate(String timeStr, int minutes) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        long timeLong = 0;
        long timeAdded = 0;

        date = df.parse(timeStr);
        timeLong = date.getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minutes);
        timeAdded = c.getTimeInMillis();
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
        // String dayAfter = new
        // SimpleDateFormat("yyyy-MM-dd").format(timeAdded);
        return dayBefore;
    }

    /**
     * 获取两个时间之间的月份数
     *
     * @param beginDate 2012-02-03/2012-2-3
     * @param endDate   2012-05-30/2012-5-30
     * @return
     */
    public static Integer getMonths(String beginDate, String endDate) {
        int months = 0;
        int beginYear = Integer.parseInt(beginDate.substring(0, 4));
        int endYear = Integer.parseInt(endDate.substring(0, 4));
        int beginMonth = 0;
        if (beginDate.substring(5, 7).indexOf("-") == -1) {
            beginMonth = Integer.parseInt(beginDate.substring(5, 7));
        } else {
            beginMonth = Integer.parseInt(beginDate.substring(5, 6));
        }
        int endMonth = 0;
        if (endDate.substring(5, 7).indexOf("-") == -1) {
            endMonth = Integer.parseInt(endDate.substring(5, 7));
        } else {
            endMonth = Integer.parseInt(endDate.substring(5, 6));
        }

        int years = endYear - beginYear;
        if (years == 0) {
            months = endMonth - beginMonth + 1;
        } else {
            months = 12 * years + endMonth - beginMonth + 1;
        }
        return months;
    }

    /**
     * 对当前日期格式的转换，转换成("yyyy-MM-dd HH:mm:ss")格式的方法
     *
     * @return
     */
    public static String convertToSS() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String year = formatter.format(new Date());
        return year;
    }

    /**
     * 字符串转成时间,格式为 yyyy-MM-dd HH:mm:ss yyyyMMdd HH:mm:ss yyyy.MM.dd HH:mm:ss
     *
     * @param dateString
     * @return
     */
    public static Date string2Date(String dateString) {
        Date date = null;
        if (dateString != null && dateString.trim().length() > 0) {
            try {
                if (dateString.indexOf("-") > 0) {
                    date = df.parse(dateString);
                } else if (dateString.indexOf(".") > 0) {
                    date = df5.parse(dateString);
                } else {
                    date = df3.parse(dateString);
                }
            } catch (Exception pe) {
                log.error("字符串转Date错误, 字符串为：" + dateString, pe);
            }
        }
        return date;
    }

    /**
     * 字符串转成时间,格式为 yyyy-MM-dd yyyy.MM.dd yyyyMMdd
     *
     * @param dateString
     * @return
     */
    public static Date string2Date2(String dateString) {
        Date date = null;
        if (dateString != null && dateString.trim().length() > 0) {
            try {
                if (dateString.indexOf("-") > 0) {
                    date = df2.parse(dateString);
                } else if (dateString.indexOf(".") > 0) {
                    date = df6.parse(dateString);
                } else {
                    date = df4.parse(dateString);
                }
            } catch (Exception pe) {
                log.error("字符串转Date错误, 字符串为：" + dateString, pe);
            }
        }
        return date;
    }

    /**
     * 字符串转成时间 日期保留,开始时间, yyyy-MM-dd HH:mm:ss yyyy.MM.dd HH:mm:ss yyyyMMdd
     * HH:mm:ss 时间部分为 00:00:00
     *
     * @param dateString
     * @return
     */
    public static Date string2DateBegin(String dateString) {
        Date date = null;
        if (dateString != null && dateString.trim().length() > 0) {
            try {
                if (dateString.indexOf("-") > 0) {
                    date = df.parse(dateString + " 00:00:00");
                } else if (dateString.indexOf(".") > 0) {
                    date = df5.parse(dateString + " 00:00:00");
                } else {
                    date = df3.parse(dateString + " 00:00:00");
                }
            } catch (Exception pe) {
                log.error("字符串转Date错误, 字符串为：" + dateString, pe);
            }
        }
        return date;
    }

    /**
     * 字符串转成时间 日期保留,结束时间, yyyy-MM-dd HH:mm:ss yyyy.MM.dd HH:mm:ss yyyyMMdd
     * HH:mm:ss 时间部分为 23:59:59
     *
     * @param dateString
     * @return
     */
    public static Date string2DateEnd(String dateString) {
        Date date = null;
        if (dateString != null && dateString.trim().length() > 0) {
            try {
                if (dateString.indexOf("-") > 0) {
                    date = df.parse(dateString + " 23:59:59");
                } else if (dateString.indexOf(".") > 0) {
                    date = df5.parse(dateString + " 23:59:59");
                } else {
                    date = df3.parse(dateString + " 23:59:59");
                }
            } catch (Exception pe) {
                log.error("字符串转Date错误, 字符串为：" + dateString, pe);
            }
        }
        return date;
    }

    /**
     * 获得指定时间的前n分钟的时间
     */
    public static Date getNewDateTime(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int minute = c.get(Calendar.MINUTE);
        c.set(Calendar.MINUTE, minute - n);

        String minuteAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
        return DateUtils.getStringToDate(minuteAfter);
    }

    /**
     * 对日期格式的转换成("yyyy-MM-dd)格式的方法
     *
     * @param str
     * @return
     */
    public static java.sql.Date Convert(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(str);
            java.sql.Date d1 = new java.sql.Date(d.getTime());
            return d1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 获得指定日期的前N天 的日期，往前推
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayByMinuteNum(String specifiedDay, int num) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - num);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 这个方法在给定的日期基础上，增加或减少指定的月数，返回计算后得到的日期
     *
     * @param specifiedDay 要操作的起点日期
     * @param monthCount   向前或向后的月数
     */
    public static Date getSpecifiedDateByMonths(Date specifiedDay, int monthCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(specifiedDay);
        calendar.add(Calendar.MONTH, monthCount);
        return calendar.getTime();
    }

    /**
     * 这个方法在给定的日期基础上，增加或减少指定的月数，返回计算后得到的日期,最后再减去一面
     * 本方法用于根据开始使用时间和购买时间获得结束使用时间
     *
     * @param specifiedDay 要操作的起点日期
     * @param monthCount   向前或向后的月数
     */
    public static Date getEndUseTimeByMonths(Date specifiedDay, int monthCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(specifiedDay);
        calendar.add(Calendar.MONTH, monthCount);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    /**
     * 这个方法获得明天的零点零分零秒
     * 用于获得默认的开始使用时间
     *
     * @return
     */
    public static Date getDefaultStartUseTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 在指定的日期基础上，增加或减少指定的天数，返回计算后得到的日期
     *
     * @param specifiedDay 操作起点日期
     * @param dayCount     增加或减少的天数
     * @return 计算后得到的日期
     * @since 2014-08-24
     */
    public static Date getSpecifiedDateByDays(Date specifiedDay, int dayCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(specifiedDay);
        calendar.add(Calendar.DAY_OF_YEAR, dayCount);
        return calendar.getTime();
    }

    /**
     * 根据传入的时间字符串和要格式化成的格式字符串得到指定的时间。
     *
     * @param str    时间
     * @param format 格式化
     * @return 指定格式化的时间
     * @since 2014-8-25 上午10:12
     */
    public static Date convertByFormat(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date d = sdf.parse(str);
            Date d1 = new Date(d.getTime());
            return d1;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将传入的日期转换为年月日时分秒的格式,并返回该字符串。
     * <p/>
     *
     * @param date 传入日期参数，此参数不能为empty。
     * @return 指定的日期转换的字符串。
     * @since 2014-8-25 上午10:55
     */
    public static String convertToSS(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN_yyyy_MM_dd_HH_MM_ss);
        String dateToString = format.format(date);
        return dateToString;
    }

    public static Date getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_yyyyMMdd);
        String currentDateString = simpleDateFormat.format(currentDate);
        try {
            currentDate = simpleDateFormat.parse(currentDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate;
    }

    /**
     * 这个方法用于获得当前时间到明天零点零分零秒之间的秒数，做为订单编号在Redis中key的过期时间
     *
     * @return redis中订单编号的key即将过期的秒数
     * @since 2014-09-29
     */
    public static int getOrderNumberExpireSeconds() {
        Date current = new Date();
        Date endOfToday = calculateEndOfDay(current);
        return (int) ((endOfToday.getTime() - current.getTime()) / 1000 + 1);
    }


    /**
     * 这个方法用于获得当前时间到明天零点零分零秒之间的秒数，做为用户编号在Redis中key的过期时间
     *
     * @return redis中用户编号的key即将过期的秒数
     * @since 2014-09-29
     */
    public static int getUserNumberExpireSeconds() {
        Date current = new Date();
        Date endOfToday = calculateEndOfDay(current);
        return (int) ((endOfToday.getTime() - current.getTime()) / 1000 + 1);
    }

    /**
     * 这个方法用于获得当前时间到明天零点零分零秒之间的秒数，做为订单编号在Redis中key的过期时间
     *
     * @return redis中订单编号的key即将过期的秒数
     */
    public static int getTransferNumberExpireSeconds() {
        Date current = new Date();
        Date endOfToday = calculateEndOfDay(current);
        return (int) ((endOfToday.getTime() - current.getTime()) / 1000 + 1);
    }
    public static String parseDateToLongString(Date date) {
        return LONGDATEFORMAT.format(date);
    }
}
