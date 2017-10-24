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

    public static void setSchoolName(int schoolId, String schoolName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(OTHER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.SCHOOL_ID + schoolId, schoolName);
        editor.apply();
    }

    public static String getSchoolName(int schoolId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(OTHER_INFO, Context.MODE_APPEND);
        return sharedPreferences.getString(C.SCHOOL_ID + schoolId, "");
    }


    public static void setSchoolId(int schoolId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(OTHER_INFO, Context.MODE_PRIVATE).edit();
        editor.putInt(C.SCHOOL_ID, schoolId);
        editor.apply();
    }

    /**
     * 默认返回重庆邮电大学
     *
     * @return 学校的ID
     */
    public static int getSchoolId() {
        SharedPreferences preferences = context.getSharedPreferences(OTHER_INFO, Context.MODE_PRIVATE);
        return preferences.getInt(C.SCHOOL_ID, 1);//setStringToNet()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
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

    public static void setClearTime(int type,Long time){
        SharedPreferences.Editor editor = context.getSharedPreferences(MESSAGE_INFO, Context.MODE_PRIVATE).edit();
        editor.putLong(type+"",time);
        editor.apply();

    }
    public static long getClearTime(int type){
        SharedPreferences preferences = context.getSharedPreferences(MESSAGE_INFO, Context.MODE_PRIVATE);
        return preferences.getLong(type+"",0);
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

    public static void setTopicField(int id) {
        SharedPreferences.Editor editor = context.getSharedPreferences(OTHER_INFO, Context.MODE_PRIVATE).edit();
        editor.putInt(C.TOPIC_Field, id);
        editor.apply();
    }

    public static int getTopicField(int id) {
        SharedPreferences preferences = context.getSharedPreferences(OTHER_INFO, Context.MODE_PRIVATE);
        return preferences.getInt(C.TOPIC_Field, id);
    }


    public static void setTopicAnswerLike(int id, boolean isLike) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TOPIC_LIKE, Context.MODE_PRIVATE).edit();
        editor.putBoolean(id + "", isLike);
        editor.apply();
    }



    public static boolean getTopicAnswerLike(int id) {
        SharedPreferences preferences = context.getSharedPreferences(TOPIC_LIKE, Context.MODE_PRIVATE);
        return preferences.getBoolean(id + "", false);
    }

    public static void setTopicDetailFollow(int topicId, boolean isFollow){
        SharedPreferences.Editor editor = context.getSharedPreferences(TOPIC_FOLLOW, Context.MODE_PRIVATE).edit();
        editor.putBoolean(topicId + "", isFollow);
        editor.apply();
    }

    public static boolean getTopicDetailFollow(int topicId){
        SharedPreferences preferences = context.getSharedPreferences(TOPIC_FOLLOW, Context.MODE_PRIVATE);
        return preferences.getBoolean(topicId + "", false);
    }


    public static void setOrderUserName(String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ORDER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.ORDERNAME, name);
        editor.apply();
    }


    public static String getOrderUserName() {
        SharedPreferences preferences = context.getSharedPreferences(ORDER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.ORDERNAME, "");
    }


    public static void setOrderNumber(String num) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ORDER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.ORDERNUMBER, num);
        editor.apply();
    }


    public static String getOrderNumber() {
        SharedPreferences preferences = context.getSharedPreferences(ORDER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.ORDERNUMBER, "");
    }

    public static void setAutoDownLoad(boolean isAutoDownLoad){
        SharedPreferences.Editor editor = context.getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE).edit();
        editor.putBoolean(C.IS_AUTO_DOWNLOAD, isAutoDownLoad);
        editor.apply();
    }

    public static boolean getAutoDownLoad(){
        SharedPreferences preferences = context.getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE);
        return preferences.getBoolean(C.IS_AUTO_DOWNLOAD,true);
    }


    public static void setFirstNoPass(boolean isAutoDownLoad){
        SharedPreferences.Editor editor = context.getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE).edit();
        editor.putBoolean(C.IS_FIRST_NO_PASS,isAutoDownLoad);
        editor.apply();
    }

    public static boolean getFirstNoPass(){
        SharedPreferences preferences = context.getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE);
        return preferences.getBoolean(C.IS_FIRST_NO_PASS,true);
    }

//    public static void clear

}
