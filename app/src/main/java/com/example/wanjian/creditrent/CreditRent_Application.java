package com.example.wanjian.creditrent;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.example.wanjian.creditrent.base.Global;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.CreditRent_Context;
import com.example.wanjian.creditrent.common.util.CrashHandler;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.moudles.chat.CustomUserProvider;
import com.facebook.stetho.Stetho;
import com.mob.MobApplication;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.leancloud.chatkit.LCChatKit;

/**
 * Created by Jianwan on 2017/7/25.
 */


public class CreditRent_Application extends MobApplication {

    public static final String TAG = "CreditRent_Application";
    private HashMap<WeakReference<Activity>, Integer> activities = new HashMap<>(3);
    private WeakReference<Activity> mCurrentActivityRef;
    private static Context mContext;
    public static String cacheDir = "";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        long startTime = System.currentTimeMillis();
        Log.e("startTime", startTime + "");
        CreditRent_Context.setApplicaiton(this);
        MultiDex.install(this);
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Global.init(this);


        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"GQAiwzHhin5lvwc0PHtf1YGq-gzGzoHsz","JgW8AYQMKHOzHK4gcipCR5T1");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);

        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        LCChatKit.getInstance().init(getApplicationContext(), "GQAiwzHhin5lvwc0PHtf1YGq-gzGzoHsz", "JgW8AYQMKHOzHK4gcipCR5T1");

        mContext = this;
        // 初始化 retrofit
        RetrofitNewSingleton.getInstance();
        SharedPreferencesUtil.init(getApplicationContext());
        CrashHandler.init(new CrashHandler(getApplicationContext()));
        if(!BuildConfig.DEBUG){
//            FIR.init(this);
            // CrashReport.initCrashReport(getApplicationContext(), "900030389", lse);
        }else{
            // chrome://inspect/ 利用 chrome 调试
            Stetho.initializeWithDefaults(this);
        }
//内存泄漏查看
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
//        LeakCanary.install(this);
        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        if (getApplicationContext().getExternalCacheDir() != null) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activities.put(new WeakReference<Activity>(activity), 1);
                mCurrentActivityRef = new WeakReference<Activity>(activity);
            }
            @Override
            public void onActivityStarted(Activity activity) {
            }
            @Override
            public void onActivityResumed(Activity activity) {
            }
            @Override
            public void onActivityPaused(Activity activity) {
            }
            @Override
            public void onActivityStopped(Activity activity) {
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
            @Override
            public void onActivityDestroyed(Activity activity) {
                Iterator iter = activities.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    WeakReference<Activity> act = (WeakReference<Activity>) entry.getKey();
                    if (act.get() == activity) {
                        iter.remove();
                        break;
                    }
                }
                if (mCurrentActivityRef != null) {
                    Activity curr = mCurrentActivityRef.get();
                    if (curr == activity) {
                        mCurrentActivityRef = null;
                    }
                }
            }
        });
    }
    public static Context getContext() {
        return mContext;
    }
    public void finishAllActivity() {
        Log.i(TAG, "finish all activities");
        Iterator iter = activities.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            WeakReference<Activity> act = (WeakReference<Activity>) entry.getKey();
            Activity _act = act.get();
            if (_act != null && !_act.isFinishing()) {
                _act.finish();
            }
        }
    }
    /**
     * 杀掉其他所有的activity
     */
    public void finishOtherActivity(Activity activity) {
        Log.i(TAG, "finishOtherActivity");
        Iterator iter = activities.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            WeakReference<Activity> act = (WeakReference<Activity>) entry.getKey();
            Activity _act = act.get();
            if (_act != null && !_act.isFinishing() && _act != activity) {
                _act.finish();
            }
        }
    }
    /**
     * 结束所有指定类型的 activity
     *
     * @param cla activity 的类型
     * @return 关闭的 activity 数量；
     */
    public int finishActivity(Class<?> cla) {
        int finishCount = 0;
        Iterator iter = activities.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            WeakReference<Activity> act = (WeakReference<Activity>) entry.getKey();
            Activity _act = act.get();
            if (_act != null && !_act.isFinishing() && cla.isInstance(_act)) {
                _act.finish();
                finishCount++;
                iter.remove();
            }
        }
        return finishCount;
    }
    /**
     * 结束除self外所有指定类型的 activity
     *
     * @param cla  activity 的类型
     * @param self 不允许关闭的activity
     * @return 关闭的 activity 数量
     */
    public int finishActivity(Class<?> cla, Object self) {
        int finishCount = 0;
        Iterator iter = activities.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            try {
                WeakReference<Activity> act = (WeakReference<Activity>) entry.getKey();
                Activity _act = act.get();
                if (_act != null && !_act.equals(self) && !_act.isFinishing() && cla.isInstance(_act)) {
                    _act.finish();
                    finishCount++;
                    iter.remove();
                }
            } catch (Exception e) {
                Log.d(TAG, "finish Activity fail!", e);
            }
        }
        return finishCount;
    }
    /**
     * 获得当前运行的activity
     */
    public Activity getCurrentActivity() {
        if (mCurrentActivityRef != null) {
            Activity ac = mCurrentActivityRef.get();
            if (ac != null && !ac.isFinishing()) {
                return ac;
            }
        }
        return null;
    }
}
