package com.example.wanjian.creditrent.common.util;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.example.wanjian.creditrent.CreditRent_Application;

/**
 * Created by Soully on 2017/7/25.
 */

public class ToastUtil {


    public static void show(String content) {
        Toast.makeText(CreditRent_Application.getContext(), content, Toast.LENGTH_SHORT).show();
    }

    public static void show(@StringRes int res) {
        Toast.makeText(CreditRent_Application.getContext(), res, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String content) {
        Toast.makeText(CreditRent_Application.getContext(), content, Toast.LENGTH_LONG).show();
    }

    public static void showLong(@StringRes int res) {
        Toast.makeText(CreditRent_Application.getContext(), res, Toast.LENGTH_SHORT).show();
    }
}

