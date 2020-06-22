package com.onysakura.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String format(Date date, String patten) {
        SimpleDateFormat format = new SimpleDateFormat(patten);
        return format.format(date);
    }
}
