package com.example.wanjian.creditrent.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMDHMSTime() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = mDateFormat.format(new Date());
        return date;
    }

    /**
     * MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowMDHMSTime() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "MM-dd HH:mm:ss");
        String date = mDateFormat.format(new Date());
        return date;
    }

    /**
     * MM-dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMD() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String date = mDateFormat.format(new Date());
        return date;
    }


    /**
     * MM-dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowMD() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return month+"月"+day+"日";
    }


    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getYMD(Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String dateS = mDateFormat.format(date);
        return dateS;
    }




    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime(String format, Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                format);
        String dateS = mDateFormat.format(date);
        return dateS;
    }
    public static String getTime(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 时间戳 转换成 指定格式的时间
     *
     * @param timeStamp 时间戳
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime(String format, long timeStamp) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                format);
        String dateS = mDateFormat.format(timeStamp*1000);
        return dateS;
    }


    public static String coverTime(Long time) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);//参数携带的时间 转化为格林威治时间
    /*
        对比当前时间和参数时间打印出是于多少时间点之后发送的消息。
    */
        if (currentCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            if (currentCalendar.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) {
                if (currentCalendar.get(Calendar.HOUR_OF_DAY) == calendar.get(Calendar.HOUR_OF_DAY)) {
                    int min = currentCalendar.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
                    if (min > 0)
                        return min + "分钟前";
                    else
                        return "刚刚";
                } else {
                    return currentCalendar.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY) + "小时前";
                }
            } else if (currentCalendar.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR) == 1) {
                // 这里没有考虑每年最后一天的判断，也就是说1.1日时，昨天的显示的是日期

                return "昨天";
            }

        }

        return DateFormatUtil.formatDate("yyyy-MM-dd", time);
    }

    /**
     * 此方法v表示要设置的控件,v1表示相邻的显示45分钟的时间差
     *
     * @param activity
     * @param v
     * @param v1
     * @param calendar
     */
    public static void showTimePickerDialog(Activity activity, final View v, final View v1, Calendar calendar) {
        new TimePickerDialog(activity,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {

                        int hourTemp = hourOfDay;
                        int minuteTemp = minute + 45;
                        if (minuteTemp >= 60) {
                            minuteTemp -= 60;
                            hourTemp++;
                        }

                        String hourChange = new StringBuilder().append(hourTemp).toString();
                        String minuteChange = new StringBuilder().append(minuteTemp).toString();
                        if (hourTemp < 10) {
                            hourChange = "0" + hourChange;
                        }
                        if (minuteTemp < 10) {
                            minuteChange = "0" + minuteChange;
                        }

                        if (v1 != null)
                            ((TextView) v1).setText(hourChange + ":" + minuteChange);

                        String hour = new StringBuilder().append(hourOfDay).toString();
                        String minutes = new StringBuilder().append(minute).toString();
                        if (hourOfDay < 10) {
                            hour = "0" + hour;
                        }
                        if (minute < 10) {
                            minutes = "0" + minutes;
                        }
                        ((TextView) v).setHint("");
                        ((TextView) v).setText(hour + ":" + minutes);
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                // true表示采用24小时制
                true).show();
    }


    public static void showDatePickerDialog(Activity activity, final View v, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String month = new StringBuilder().append(monthOfYear + 1).toString();
                        String day = new StringBuilder().append(dayOfMonth).toString();
                        if (monthOfYear < 9) {
                            month = "0" + month;
                        }
                        if (dayOfMonth < 10) {
                            day = "0" + day;
                        }
                        ((TextView) v).setText(year + "年" + month + "月" + day + "日");
                    }
                }
                // 设置初始日期
                , calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}
