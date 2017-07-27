package com.botongsoft.rfid.common.utils;

import java.util.Date;

public class ObjectFormatUtil {
    public static Integer convertVarcharToInt(String value) throws ObjectFormatException {
        if (value.equals("")) return null;
        Double db = new Double(value);
        return new Integer(db.intValue());
    }

    public static Integer convertMoneyToInt(java.math.BigDecimal value) throws ObjectFormatException {
        if (value.equals("")) return null;
        return new Integer(value.intValue());
    }

    public static java.math.BigDecimal convertVarcharToMoney(String value) throws ObjectFormatException {
        if (value.equals("")) return null;
        java.math.BigDecimal db = new java.math.BigDecimal(value);
        return db;
    }

    public static String convertVarcharToDarq(String value) throws Exception {
        if (value == null) return null;
        Date date = StringFormatUtil.getDateOfDateStr(value, "yyyyMMdd");
        return StringFormatUtil.getStringOfDate(date, "yyyyMMdd");
    }

    public static Date convertVarcharToDate(String value, String style) throws Exception {
        return StringFormatUtil.getDateOfDateStr(value, style);
    }

    public static String convertIntToVarchar(Integer value) throws Exception {
        if (value == null)
            return null;
        return "" + value.intValue();
    }

    public static String convertMoneyToVarchar(java.math.BigDecimal value) throws Exception {
        if (value == null)
            return null;
        return "" + value.intValue();
    }

    public static String convertIntToDarq(Integer value) throws Exception {
        String valueStr = null;
        if (value == null)
            valueStr = "00000000";
        else
            valueStr = value.toString();
        valueStr += "00000000";
        return valueStr.substring(0, 8);
    }

    public static Date convertIntToDate(Integer value) throws Exception {
        String valueStr = null;
        if (value == null)
            valueStr = "00000000";
        else
            valueStr = value.toString();
        valueStr += "00000000";
        valueStr = valueStr.substring(0, 8);
        Date date = StringFormatUtil.getDateOfDateStr(valueStr, "yyyyMMdd");
        return date;
    }

    public static String convertDateToVarchar(Date value, String style) throws Exception {
        return StringFormatUtil.getStringOfDate(value, style);
    }

    public static Integer convertDateToInt(Date value) throws Exception {
        return new Integer(convertDateToVarchar(value, "yyyyMMdd"));
    }

    public static java.math.BigDecimal convertDateToMoney(Date value) throws Exception {
        return new java.math.BigDecimal(convertDateToVarchar(value, "yyyyMMdd"));
    }

    public static String convertDateToDarq(Date value) throws Exception {
        return StringFormatUtil.getStringOfDate(value, "yyyyMMdd");
    }

    public static String convertDarqToVarchar(String value) throws Exception {
        return value;
    }

    public static Integer convertDarqToInt(String value) throws Exception {
        return new Integer(value);
    }

    public static java.math.BigDecimal convertDarqToMoney(String value) throws Exception {
        return new java.math.BigDecimal(value);
    }

    public static Date convertDarqToDate(String value) throws Exception {
        return convertVarcharToDate(value, "yyyyMMdd");
    }

}
