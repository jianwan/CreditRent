package com.example.wanjian.creditrent.moudles.user.moudles.user_information;

import android.content.Intent;
import android.os.Bundle;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by wanjian on 2018/4/8.
 */

public class UserVerificationActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detailinformation_verification);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);
    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(UserVerificationActivity.this, UserDetailInformation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
