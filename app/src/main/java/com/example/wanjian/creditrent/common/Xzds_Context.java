package com.example.wanjian.creditrent.common;

import android.content.Context;

import com.example.wanjian.creditrent.CreditRent_Application;
import com.example.wanjian.creditrent.base.Global;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Soully on 2017/7/26.
 */

public final class Xzds_Context {
    private final static AtomicReference<CreditRent_Application> aApplicaiton = new AtomicReference<>();
    public static Context getApplicationContext(){
        return Global.getApplicationContext();
    }
    public static void setApplicaiton(CreditRent_Application applicaiton){


        if(applicaiton == null){
            throw new NullPointerException("applicaiton can not be null");
        }
        if(aApplicaiton.getAndSet(applicaiton) != null){
            throw new IllegalStateException("application can only be set once");
        }
    }

    public static CreditRent_Application getApplication(){
        return aApplicaiton.get();
    }
}
