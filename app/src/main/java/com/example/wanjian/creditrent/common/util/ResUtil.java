package com.example.wanjian.creditrent.common.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.example.wanjian.creditrent.CreditRent_Application;

/**
 * Created by HugoXie on 16/5/2.
 *
 * Email: Hugo3641@gamil.com
 * GitHub: https://github.com/xcc3641
 * 资源的获取 统一方法
 */
public class ResUtil {

    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(CreditRent_Application.getContext(), id);
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(CreditRent_Application.getContext(), id);
    }
    public static String getString(@StringRes int id){
        return CreditRent_Application.getContext().getString(id);
    }
}
