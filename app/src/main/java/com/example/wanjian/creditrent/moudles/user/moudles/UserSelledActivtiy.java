package com.example.wanjian.creditrent.moudles.user.moudles;

import android.os.Bundle;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by wanjian on 2017/11/8.
 * 我卖出去的物品
 */

public class UserSelledActivtiy extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_selled);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);

    }
}
