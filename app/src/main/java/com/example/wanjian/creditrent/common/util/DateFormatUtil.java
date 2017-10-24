package com.example.wanjian.creditrent.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/22.
 */
public class DateFormatUtil {

    public static final String DEFAULT_FORMAT = "yyyy年MM月dd日";


    private static final String TAG = DateFormatUtil.class.getSimpleName();

    /**
     * 将服务器返回的时间戳转化为需要的日期格式
     *
     * @param format 需要的日期格式 如 "yyyy-MM-dd HH:mm:ss"
     * @param timestamp 时间戳
     */
    public static String formatDate(String format, Long timestamp) {

        try {
            SimpleDateFormat f = new SimpleDateFormat(format == null ? DEFAULT_FORMAT : format);
            return f.format(timestamp);
        }catch (Exception e){
            PLog.e("formatDate?"+e.toString());
        }
        return "-1";
    }

    public static String formatTime(String format, Long timestamp) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date(timestamp));
            return date;
        }catch (Exception e){
            PLog.e("formatDate?"+e.toString());
        }
        return "-1";
    }

    /**
     * 将字符串转化成时间戳
     *
     * @param user_time
     * @return
     */
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        re_time = re_time + "000";
        return re_time;
    }

    public static String getTimer(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        re_time = re_time + "000";
        return re_time;
    }

    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;
    }



}
