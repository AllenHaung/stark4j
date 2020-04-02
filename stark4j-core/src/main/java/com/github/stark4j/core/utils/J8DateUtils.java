package com.github.stark4j.core.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * @author Allen Created 2020/3/27
 **/
public final class J8DateUtils {
    //Java8中，DateTimeFormatter是线程安全的，所以可以定义全局的格式并可以直接使用.
    /**
     * 全局的时间日期格式
     */
    public static DateTimeFormatter GLOBAL_DATE_TIME_FORMATTER = createDateTimeFormatter("yyyy-MM-dd HH:mm:ss");

    /**
     * 全局日期格式
     */
    public static DateTimeFormatter GLOBAL_DATE_FORMATTER = createDateTimeFormatter("yyyy-MM-dd");

    /**
     * 全局的时间格式
     */
    public static DateTimeFormatter GLOBAL_TIME_FORMATTER = createDateTimeFormatter("HH:mm:ss");

    /**
     * 全局小时格式
     */
    public static DateTimeFormatter GLOBAL_HOUR_FORMATTER = createDateTimeFormatter("HH");

    /**
     * 全局月份格式
     */
    public static DateTimeFormatter GLOBAL_MONTH_FORMATTER = createDateTimeFormatter("yyyy-MM");
    /**
     * 全局的时区对象
     */
    public static ZoneOffset GLOBAL_ZONE_OFFSET = createZoneOffset("+8");

    /**
     * 给某时间加几天。需要注意的是，该方法会返回一个新的对象，用于取代原对象
     *
     * @param date 需要相加的时间对象
     * @param days 天数
     * @return 返回相加后的新的时间对象
     */
    public static LocalDateTime addDays(LocalDateTime date, long days) {
        return date.plusDays(days);
    }

    /**
     * 给某时间加几天。需要注意的是，该方法会返回一个新的对象，用于取代原对象
     *
     * @param date 需要相加的时间对象
     * @param days 天数
     * @return 返回相加后的新的时间对象
     */
    public static LocalDate addDays(LocalDate date, long days) {
        return date.plusDays(days);
    }

    /**
     * 给时间加几个星期，需要注意的是，该方法会返回一个新的对象，用于取代原对象
     *
     * @param date 需要相加的时间对象
     * @param week 星期数
     * @return 返回相加后的新的时间对象
     */
    public static LocalDateTime addWeek(LocalDateTime date, long week) {
        return date.plusWeeks(week);
    }

    /**
     * @param date1 某时间
     * @param date2 比较的时间
     * @return 返回某时间时候在比较的时间之前
     */
    public static boolean isBefore(LocalDateTime date1, LocalDateTime date2) {
        return date1.isBefore(date2);
    }

    /**
     * @param date1 某时间
     * @param date2 比较的时间
     * @return 返回某时间时候在比较的时间之前
     */
    public static boolean isBefore(LocalDate date1, LocalDate date2) {
        return date1.isBefore(date2);
    }

    /**
     * @param date1 某时间
     * @param date2 比较的时间
     * @return 返回某时间时候在比较的时间之前
     */
    public static boolean isBefore(LocalTime date1, LocalTime date2) {
        return date1.isBefore(date2);
    }

    /**
     * @param date1 某时间
     * @param date2 比较的时间
     * @return 返回某时间时候在比较的时间之后
     */
    public static boolean isAfter(LocalDateTime date1, LocalDateTime date2) {
        return date1.isAfter(date2);
    }

    /**
     * @param date1 某时间
     * @param date2 比较的时间
     * @return 返回某时间时候在比较的时间之后
     */
    public static boolean isAfter(LocalDate date1, LocalDate date2) {
        return date1.isAfter(date2);
    }

    /**
     * @param date1 某时间
     * @param date2 比较的时间
     * @return 返回某时间时候在比较的时间之后
     */
    public static boolean isAfter(LocalTime date1, LocalTime date2) {
        return date1.isAfter(date2);
    }

    /**
     * 计算两个时间之间的分钟数
     *
     * @param startInclusive 开始时间/LocalDateTime/LocalDate/LocalTime
     * @param endExclusive   结束时间/LocalDateTime/LocalDate/LocalTime
     * @return 返回分钟数
     */
    public static long betweenMinutes(Temporal startInclusive, Temporal endExclusive) {
        return between(startInclusive, endExclusive).toMinutes();
    }

    /**
     * 两个时间之间的毫秒数
     *
     * @param startInclusive 开始时间/LocalDateTime/LocalDate/LocalTime
     * @param endExclusive   结束时间/LocalDateTime/LocalDate/LocalTime
     * @return 返回毫秒数
     */
    public static long betweenMillis(Temporal startInclusive, Temporal endExclusive) {
        return between(startInclusive, endExclusive).toMillis();
    }

    /**
     * 两个时间之间的小时数
     *
     * @param startInclusive 开始时间/LocalDateTime/LocalDate/LocalTime
     * @param endExclusive   结束时间/LocalDateTime/LocalDate/LocalTime
     * @return 返回小时
     * 数
     */
    public static long betweenHours(Temporal startInclusive, Temporal endExclusive) {
        return between(startInclusive, endExclusive).toHours();
    }

    /**
     * 两个时间之间的天数
     *
     * @param startInclusive 开始时间/LocalDateTime/LocalDate/LocalTime
     * @param endExclusive   结束时间/LocalDateTime/LocalDate/LocalTime
     * @return 返回天数
     */
    public static long betweenDays(Temporal startInclusive, Temporal endExclusive) {
        return between(startInclusive, endExclusive).toDays();
    }

    /**
     * 两个时间之间的天数
     *
     * @param startInclusive 开始时间/LocalDateTime/LocalDate/LocalTime
     * @param endExclusive   结束时间/LocalDateTime/LocalDate/LocalTime
     * @return 返回天数
     */
    public static int betweenDays(LocalDate startInclusive,LocalDate endExclusive){
        return startInclusive.until(endExclusive).getDays();
    }

    /**
     * 返回两个时间之间的间隔
     *
     * @param startInclusive 开始时间/LocalDateTime/LocalDate/LocalTime
     * @param endExclusive   结束时间/LocalDateTime/LocalDate/LocalTime
     * @return 返回时间间隔
     */
    public static Duration between(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive);
    }

    /**
     * 将字符串转换成LocalDateTime对象
     *
     * @param dateString 要解析的时间字符串
     * @param formatter  时间格式化对象
     * @return 返回解析的时间对象
     */
    public static LocalDateTime parseFromString(String dateString, DateTimeFormatter formatter) {
        return LocalDateTime.parse(dateString, formatter);
    }

    /**
     * 将字符串转换成LocalDateTime对象
     *
     * @param dateString 要解析的时间字符串
     * @param formatter  格式化字符串。例如yyyy-MM-dd HH:mm:ss
     * @return 返回解析的时间对象
     */
    public static LocalDateTime parseFromString(String dateString, String formatter) {
        return LocalDateTime.parse(dateString, createDateTimeFormatter(formatter));
    }

    /**
     * 将字符串转换成LocalDateTime对象。时间格式为默认的格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param dateString 要解析的时间字符串
     * @return 返回解析的时间对象
     */
    public static LocalDateTime parseFromString(String dateString) {
        return LocalDateTime.parse(dateString, GLOBAL_DATE_TIME_FORMATTER);
    }

    /**
     * 将时间对象转换成秒
     *
     * @param date   时间对象
     * @param offset 时区
     * @return 返回秒
     */
    public static long parseSecond(LocalDateTime date, ZoneOffset offset) {
        return date.toEpochSecond(offset);
    }

    /**
     * 将时间对象转换成秒
     *
     * @param date   时间对象
     * @param offset 时区，例如 +8 表示东八区
     * @return 返回秒
     */
    public static long parseSecond(LocalDateTime date, String offset) {
        return date.toEpochSecond(createZoneOffset(offset));
    }

    /**
     * 将时间对象转换成秒，采用系统默认的时区进行转换
     *
     * @param date 时间对象
     * @return 返回秒
     */
    public static long parseSecond(LocalDateTime date) {
        return date.toEpochSecond(GLOBAL_ZONE_OFFSET);
    }

    /**
     * 将时间对象转换成毫秒
     *
     * @param date   时间对象
     * @param offset 时区
     * @return 返回毫秒
     */
    public static long parseMilli(LocalDateTime date, ZoneOffset offset) {
        return date.toInstant(offset).toEpochMilli();
    }

    /**
     * 将时间对象转换成毫秒
     *
     * @param date   时间对象
     * @param offset 时区，例如 +8 表示东八区
     * @return 返回毫秒
     */
    public static long parseMilli(LocalDateTime date, String offset) {
        return date.toInstant(createZoneOffset(offset)).toEpochMilli();
    }

    /**
     * 将时间对象转换成毫秒，采用系统默认的时区进行转换
     *
     * @param date 时间对象
     * @return 返回毫秒
     */
    public static long parseMilli(LocalDateTime date) {
        return date.toInstant(GLOBAL_ZONE_OFFSET).toEpochMilli();
    }

    /**
     * 将时间对象转换成时间字符串
     *
     * @param date      需要进行转换的时间对象
     * @param formatter 时间格式化对象
     * @return 返回时间字符串
     */
    public static String format(LocalDateTime date, DateTimeFormatter formatter) {
        return date.format(formatter);
    }

    /**
     * 将时间对象转换成时间字符串
     *
     * @param date      需要进行转换的时间对象
     * @param formatter 需要格式化的时间格式，例如yyyy-MM-dd HH:mm:ss
     * @return 返回时间字符串
     */
    public static String format(LocalDateTime date, String formatter) {
        return date.format(createDateTimeFormatter(formatter));
    }

    /**
     * 将时间对象转换成时间字符串.将转换成默认的时间格式
     *
     * @param date 需要进行转换的时间对象(yyyy-MM-dd HH:mm:ss)
     * @return 返回时间字符串
     */
    public static String format(LocalDateTime date) {
        return date.format(GLOBAL_DATE_TIME_FORMATTER);
    }

    /**
     * 将时间对象转换成时间字符串
     *
     * @param date      需要进行转换的时间对象
     * @param formatter 时间格式化对象
     * @return 返回时间字符串
     */
    public static String format(LocalDate date, DateTimeFormatter formatter) {
        return date.format(formatter);
    }

    /**
     * 将时间对象转换成时间字符串
     *
     * @param date      需要进行转换的时间对象
     * @param formatter 需要格式化的时间格式，例如yyyy-MM-dd
     * @return 返回时间字符串
     */
    public static String format(LocalDate date, String formatter) {
        return date.format(createDateTimeFormatter(formatter));
    }

    /**
     * 将时间对象转换成时间字符串.将转换成默认的时间格式
     *
     * @param date 需要进行转换的时间对象(yyyy-MM-dd)
     * @return 返回时间字符串
     */
    public static String format(LocalDate date) {
        return date.format(GLOBAL_DATE_FORMATTER);
    }

    /**
     * 将 时间对象转换成字符串
     *
     * @param dateTime 需要进行转换的时间对象（yyyy-MM-dd HH:mm:ss）
     * @return 返回时间字符串 HH
     */
    public static String formatHour(LocalDateTime dateTime) {
        return dateTime.format(GLOBAL_HOUR_FORMATTER);
    }

    /**
     * 将 时间对象转换成字符串
     *
     * @param dateTime 需要进行转换的时间对象（yyyy-MM-dd HH:mm:ss）
     * @return 返回时间字符串 yyyy-MM-dd
     */
    public static String formatDay(LocalDateTime dateTime) {
        return dateTime.format(GLOBAL_DATE_FORMATTER);
    }

    /**
     * 将 时间对象转换成字符串
     *
     * @param dateTime 需要进行转换的时间对象（yyyy-MM-dd HH:mm:ss）
     * @return 返回时间字符串 yyyy-MM
     */
    public static String formatMonth(LocalDateTime dateTime) {
        return dateTime.format(GLOBAL_MONTH_FORMATTER);
    }
    /**
     * 将时间对象转换成时间字符串
     *
     * @param time      需要进行转换的时间对象
     * @param formatter 时间格式化对象
     * @return 返回时间字符串
     */
    public static String format(LocalTime time, DateTimeFormatter formatter) {
        return time.format(formatter);
    }

    /**
     * 将时间对象转换成时间字符串
     *
     * @param time      需要进行转换的时间对象
     * @param formatter 需要格式化的时间格式，例如HH:mm:ss
     * @return 返回时间字符串
     */
    public static String format(LocalTime time, String formatter) {
        return time.format(createDateTimeFormatter(formatter));
    }

    /**
     * 将时间对象转换成时间字符串.将转换成默认的时间格式
     *
     * @param time 需要进行转换的时间对象(HH:mm:ss)
     * @return 返回时间字符串
     */
    public static String format(LocalTime time) {
        return time.format(GLOBAL_TIME_FORMATTER);
    }

    /**
     * 创建一个时间格式化对象
     *
     * @param formatter 例如yyyy-MM-dd HH:hh:ss
     * @return 返回时间格式化对象
     */
    public static DateTimeFormatter createDateTimeFormatter(String formatter) {
        return DateTimeFormatter.ofPattern(formatter);
    }

    /**
     * 创建一个时区对象
     *
     * @param offset 例如东八区，+8
     * @return 返回时区对象
     */
    public static ZoneOffset createZoneOffset(String offset) {
        return ZoneOffset.of(offset);
    }

    /**
     * 采用默认的时区，将Date类型转换成LocalDateTime对象
     *
     * @param date 需要转换的时间对象
     * @return 返回转换后的LocalDateTime对象
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return toLocalDateTime(date, zoneId);
    }

    /**
     * 将Date类型转换成LocalDateTime对象
     *
     * @param date   需要转换的时间对象
     * @param zoneId 时区ID
     * @return 返回转换后的LocalDateTime对象
     */
    public static LocalDateTime toLocalDateTime(Date date, ZoneId zoneId) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * 采用默认的时区，将Date类型转换成LocalDate对象
     *
     * @param date 需要转换的时间对象
     * @return 返回转换后的LocalDate对象
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * 将Date类型转换成LocalDate对象
     *
     * @param date   需要转换的时间对象
     * @param zoneId 时区ID
     * @return 返回转换后的LocalDate对象
     */
    public static LocalDate toLocalDate(Date date, ZoneId zoneId) {
        return toLocalDateTime(date, zoneId).toLocalDate();
    }

    /**
     * 采用默认的时区，将Date类型转换成LocalTime对象
     *
     * @param date   需要转换的时间对象
     * @param zoneId 时区ID
     * @return 返回转换后的LocalTime对象
     */
    public static LocalTime toLocalTime(Date date, ZoneId zoneId) {
        return toLocalDateTime(date, zoneId).toLocalTime();
    }

    /**
     * 将Date类型转换成LocalTime对象
     *
     * @param date 需要转换的时间对象
     * @return 返回转换后的LocalTime对象
     */
    public static LocalTime toLocalTime(Date date) {
        return toLocalDateTime(date).toLocalTime();
    }

    /**
     * @return 返回当前时间是本周的第几天
     */
    public static int getDayOfWeek() {
        return getDayOfWeek(LocalDate.now());
    }

    /**
     * @param date 指定的时间
     * @return 返回该时间是时间所在周的第几天
     */
    public static int getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }

    /**
     * @return 返回当前时间是本月的第几天
     */
    public static int getDayOfMonth() {
        return getDayOfMonth(LocalDate.now());
    }

    /**
     * @param date 指定的时间
     * @return 返回指定时间是该时间所在月的第几天
     */
    public static int getDayOfMonth(LocalDate date) {
        return date.getDayOfMonth();
    }

    /**
     * @return 返回当前时间是该年的第几天
     */
    public static int getDayOfYear() {
        return getDayOfYear(LocalDate.now());
    }

    /**
     * @param date 指定的时间
     * @return 返回指定时间是该时间所在年的第几天
     */
    public static int getDayOfYear(LocalDate date) {
        return LocalDate.now().getDayOfYear();
    }

    /**
     * @return 返回当前时间是第几个月
     */
    public static int getMonth() {
        return getMonth(LocalDate.now());
    }

    /**
     * @param date 指定的时间
     * @return 返回指定时间是该时间所在年的地几个月
     */
    public static int getMonth(LocalDate date) {
        return LocalDate.now().getMonthValue();
    }
}
