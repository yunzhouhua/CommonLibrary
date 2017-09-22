package com.yunzhou.libcommon.utils;

import android.util.ArrayMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 用于时间转换工具类
 * Created by huayunzhou on 2017/9/22.
 */

public class DateFormatUtils {

    /**
     * 年    2017
     */
    public static String FORMAT_YYYY = "yyyy";

    /**
     * 年月日  2017-09-22
     */
    public static String FORMAT_YMD_1 = "yyyy-MM-dd";

    /**
     * 年月日  2017/09/22
     */
    public static String FORMAT_YMD_2 = "yyyy/MM/dd";

    /**
     * 年月日  2017年09月22日
     */
    public static String FORMAT_YMD_3 = "yyyy年MM月dd日";

    /**
     * 月日   09/22
     */
    public static String FORMAT_MD_1 = "MM/dd";

    /**
     * 月日   09-22
     */
    public static String FORMAT_MD_2 = "MM-dd";

    /**
     * 年月日 时分秒  2017-09-20 17:21:22
     */
    public static String FORMAT_YMD_HMS_1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日 时分秒  2017年MM月dd日 17时21分22秒
     */
    public static String FORMAT_YMD_HMS_2 = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 年月日 时分   2017-09-20 17:21
     */
    public static String FORMAT_YMD_HM_1 = "yyyy-MM-dd HH:mm";

    /**
     * 年月日 时分  2017年MM月dd日 17时21分
     */
    public static String FORMAT_YMD_HM_2 = "yyyy年MM月dd日 HH时mm分";

    private static ArrayMap<String, ThreadLocal<SimpleDateFormat>> sdfMap = new ArrayMap<>();


    /**
     * 锁对象
     */
    private final static Object objLock = new Object();

    private static SimpleDateFormat getSdf(final Locale locale){
        if(sdfMap == null){
            sdfMap = new ArrayMap<>();
        }
        ThreadLocal<SimpleDateFormat> localFmt = sdfMap.get(locale.toString());
        //避免压入相同的sdf
        if(localFmt == null || localFmt.get() == null){
            synchronized (objLock){
                localFmt = sdfMap.get(locale.toString());
                if(localFmt == null || localFmt.get() == null){
                    localFmt = new ThreadLocal<SimpleDateFormat>(){
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(FORMAT_YYYY, locale);
                        }
                    };
                }
            }
            sdfMap.put(locale.toString(), localFmt);
        }

        return localFmt.get();
    }

    /**
     * 时间格式化
     * @param time              时间戳
     * @param fmtTemplate    格式化模板
     * @return  时间
     */
    public static String format(long time, String fmtTemplate){
        return format(time, fmtTemplate, Locale.getDefault());
    }

    /**
     * 时间格式化
     * @param time          时间戳
     * @param fmtTemplate   时间模板
     * @param locale        时区
     * @return  时间
     */
    public static String format(long time, String fmtTemplate, Locale locale){
        Date date = new Date(time);
        return format(date, fmtTemplate, locale);
    }


    /**
     * 格式化时间
     * @param date          日期
     * @param fmtTemplate   时间模板
     * @return  时间
     */
    public static String format(Date date, String fmtTemplate){
        return format(date, fmtTemplate, Locale.getDefault());
    }

    /**
     * 格式化时间
     * @param date          日期
     * @param fmtTemplate   时间模板
     * @param locale        时区
     * @return  时间
     */
    public static String format(Date date, String fmtTemplate, Locale locale){
        SimpleDateFormat sdf = getSdf(locale);
        sdf.applyPattern(fmtTemplate);
        return sdf.format(date);
    }

    public static String format(String time, String fmtSource, String fmtTarget){
        return format(time, fmtSource, fmtTarget, Locale.getDefault());
    }

    public static String format(String time, String fmtSource, String fmtTarget, Locale locale) {
        SimpleDateFormat sdfSource = new SimpleDateFormat(fmtSource, locale);
        SimpleDateFormat sdfTarget = getSdf(locale);
        try {
            Date date = sdfSource.parse(time);
            sdfTarget.applyPattern(fmtTarget);
            time = sdfTarget.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 根据时间获取时间戳
     * @param time              时间
     * @param fmtTemplate       时间模板
     * @return  时间戳
     */
    public static long getTimeStamp(String time, String fmtTemplate){
        return getTimeStamp(time, fmtTemplate, Locale.getDefault());
    }

    /**
     * 根据时间获取时间戳
     * @param time              时间
     * @param fmtTemplate       时间模板
     * @param locale            时区
     * @return  时间戳
     */
    private static long getTimeStamp(String time, String fmtTemplate, Locale locale) {
        SimpleDateFormat sdf = getSdf(locale);
        sdf.applyPattern(fmtTemplate);
        try {
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
