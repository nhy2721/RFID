package com.botongsoft.rfid.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class StringFormatUtil {

    /**
     * �������获取默认格式的日期值�������������
     *
     * @param dateStr String
     * @return Date
     */
    public static Date getDateOfDateStr(String dateStr) throws Exception {
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        if (dateStr == null || dateStr.equals(""))
            return null;
        try {

            return dateFmt.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定格式的日期值
     *
     * @param dateStr String
     * @return Date
     */
    public static Date getDateOfDateStr(String dateStr, String style) throws Exception {
        SimpleDateFormat dateFmt = new SimpleDateFormat(style);
        return dateFmt.parse(dateStr);
    }

    /**
     * 获取指定格式的日期值
     *
     * @param source 源
     * @return Date
     */
    public static String getDateOfGMTToDateStr(String source) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("ENGLISH", "CHINA"));
        Date myDate = sdf.parse(source);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("CHINESE", "CHINA"));
        return sdf2.format(myDate);
    }


    /**
     * 获取默认格式的日期字符串
     *
     * @param date Date
     * @return String
     */
    public static String getStringOfDate(Date date) {
        if (date != null) {
            java.text.DateFormat format = java.text.DateFormat.getDateInstance(
                    java.text.DateFormat.DEFAULT);
            return format.format(date);
        } else
            return "";
    }


    public static Calendar getCalendarOfString(String dateStr, String style) throws Exception {
        try {
            Date date = getDateOfDateStr(dateStr, style);
            Calendar cal = new GregorianCalendar();
            cal.setTime(date);
            return cal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 获取指定格式的日期字符串
     *
     * @param date    Date
     * @param pattern String
     * @return String
     */
    public static String getStringOfDate(Date date,
                                         String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        String str = null;
        sdf.applyPattern(pattern);
        str = sdf.format(date);
        return str;
    }


    /**
     * ��nullת���ɳ���Ϊ����ַ�
     *
     * @param str String
     * @return String
     */
    public static String nullToString(String str) {
        if (str == null)
            return "";
        else
            return str;
    }

    public static String getTrimString(String str) {
        if (str != null)
            str = str.trim();
        return str;
    }

}
