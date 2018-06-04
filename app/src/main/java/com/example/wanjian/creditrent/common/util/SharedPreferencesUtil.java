package com.example.wanjian.creditrent.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wanjian.creditrent.base.C;


/**
 * Created by zk on 2015/12/24.
 */
public class SharedPreferencesUtil {

    private final static String USER_INFO = "user_info";
    private final static String OTHER_INFO = "other_info";
    private final static String TOPIC_LIKE = "topic_like";
    private final static String ORDER_INFO = "order_info";
    private final static String SETTING_INFO = "setting_info";
    private final static String TOPIC_FOLLOW = "topic_follow";
    private final static String MESSAGE_INFO = "message_info";
    private static Context context;



    public static void init(Context context) {
        SharedPreferencesUtil.context = context;
    }




    public static void setVersion(String version) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.VERSION, version);
        editor.apply();
    }

    public static String getVersion() {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.VERSION, "");
    }


    public static void setUsername(String username) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.USER_NAME, username);
        editor.apply();
    }

    public static String getUsername() {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.USER_NAME, "");
    }

    public static void  setTrueName(String name){
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.TRUENAME,name);
        editor.apply();
    }

    public static String getTrueName(){
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.TRUENAME,"");
    }

    public static void setPassword(String psw) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.PASSWORD, psw);
        editor.apply();
    }

    public static String getPassword() {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.PASSWORD, "");
    }

    public static void setLogDate(String logDate) {
        SharedPreferences.Editor editor = context.getSharedPreferences(OTHER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.LOG_DATE, logDate);
        editor.apply();
    }

    public static String getLogDate() {
        SharedPreferences preferences = context.getSharedPreferences(OTHER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.LOG_DATE, "");
    }


    public static boolean getFirstNoPass(){
        SharedPreferences preferences = context.getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE);
        return preferences.getBoolean(C.IS_FIRST_NO_PASS,true);
    }


    public static boolean getIsLogin(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(USER_INFO,Context.MODE_APPEND);
        return sharedPreferences.getBoolean(C.ISLOGIN,false);
    }


    public static void  setIsLogin(Boolean isLogin){
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putBoolean(C.ISLOGIN,isLogin);
        editor.apply();
    }


    public static Integer getSexLocaiton(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(USER_INFO,Context.MODE_APPEND);
        return sharedPreferences.getInt(C.SEX_POSITION,0);
    }


    public static void  setSexLocatio(Integer sexLocation){
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putInt(C.SEX_POSITION,sexLocation);
        editor.apply();
    }

    public static Integer getSchoolLocation(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(USER_INFO,Context.MODE_APPEND);
        return sharedPreferences.getInt(C.SCHOOL_POSITION,0);
    }


    public static void  setSchoolLocaiton(Integer schoolLocaiton){
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putInt(C.SCHOOL_POSITION,schoolLocaiton);
        editor.apply();
    }

    public static void setCredit(String credit) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.CREDIT, credit);
        editor.apply();
    }

    public static String getCredit() {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.CREDIT, "");
    }

    public static void setRentNumber(Integer rentNumber) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putInt(C.RENTNUMBER, rentNumber);
        editor.apply();
    }

    public static Integer getRentNumber() {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return preferences.getInt(C.RENTNUMBER, 0);
    }

    public static void setReturnNumber(Integer returnNumber) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
        editor.putInt(C.RETURNNUMBER, returnNumber);
        editor.apply();
    }

    public static Integer getReturnNumber() {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return preferences.getInt(C.RETURNNUMBER, 0);
    }

}
