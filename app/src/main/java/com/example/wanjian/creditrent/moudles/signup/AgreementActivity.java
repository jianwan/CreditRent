package com.example.wanjian.creditrent.moudles.signup;

import android.os.Bundle;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by wanjian on 2017/10/30.
 */

public class AgreementActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_register_information);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);
    }

}
