package com.example.wanjian.creditrent.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Process;

import java.util.List;

/**
 * Created by Soully on 2017/7/26.
 */

public final class Global {
    private static Context context;
    private static boolean isDebug = false;
    public final static void init(Context ctx)
    {
        setContext(ctx);
    }

    public final static Context getContext()
    {
        if (context == null)
        {
            throw new NullPointerException("Global's Context is NULL, have your Application in manifest "
                    + "subclasses BaseApplication or Call 'Global.init(this)' in your own Application ? ");
        }

        return context;
    }

    public final static void setContext(Context context)
    {
        Global.context = context;

        try
        {
            ApplicationInfo info = context.getApplicationInfo();

            isDebug = ((info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);

            if (isDebug)
            {
                android.util.Log.w("Wns.Global.Runtime", "DEBUG is ON");
            }
        }
        catch (Exception e)
        {
            isDebug = false;
        }
    }

    /**
     * 判断当前进程是否是主进程<br>
     * <br>
     * <i>子进程的名称包含':'，以此为依据</i>
     *
     * @return 是主进程，或不是主进程<s>，这是一个问题</s>
     */
    public final static boolean isMainProcess() {
        String processName = currentProcessName();
        return processName == null ? false : (processName.indexOf(':') < 1);
    }

    public final static boolean isDebug()
    {
        return isDebug;
    }

    /**
     * 获得当前进程的进程名 <br>
     * <br>
     * <b>这个过程包含用轮询实现，所以不要总是使用</b>
     *
     * @return 当前进程的进程名，任何异常情况将得到 null
     */
    public final static String currentProcessName() {
        ActivityManager manager = (ActivityManager) Global.getSystemService(Context.ACTIVITY_SERVICE);

        if (manager == null)
        {
            return null;
        }

        List<ActivityManager.RunningAppProcessInfo> processInfos = manager.getRunningAppProcesses();

        if (processInfos == null)
        {
            return null;
        }

        int pid = Process.myPid();

        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos)
        {
            if (pid == processInfo.pid)
            {
                return processInfo.processName;
            }
        }

        return null;
    }
    public final static Object getSystemService(String name) {
        return getContext().getSystemService(name);
    }
    public final static Context getApplicationContext() {
        return getContext().getApplicationContext();
    }
}
