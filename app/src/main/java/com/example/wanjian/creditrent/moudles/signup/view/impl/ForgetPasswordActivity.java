package com.example.wanjian.creditrent.moudles.signup.view.impl;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;

/**
 * Created by wanjian on 2017/10/30.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {


    EditText forgetpsw_et_phonemumber,forgetpsw_et_phoneCode,forgetpsw_et_newPassword;
    TextView forgetpsw_tv_getPhoneCode;
    Button forgetpsw_btn_resetPassword;
    ImageButton forgetpsw_ib_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_forgetpassword);

        initView();
    }

    private void initView() {
        forgetpsw_et_phonemumber=(EditText)findViewById(R.id.forgetpsw_et_phonenumber);
        forgetpsw_et_phoneCode=(EditText)findViewById(R.id.forgetpsw_et_phoneCode);
        forgetpsw_et_newPassword=(EditText)findViewById(R.id.forgetpsw_et_newPassword);
        forgetpsw_tv_getPhoneCode=(TextView)findViewById(R.id.forgetpsw_tv_getPhoneCode);
        forgetpsw_ib_back=(ImageButton)findViewById(R.id.forgetpsw_ib_back);
        forgetpsw_btn_resetPassword=(Button)findViewById(R.id.forgetpsw_btn_resetPassword);

        forgetpsw_tv_getPhoneCode.setOnClickListener(this);
        forgetpsw_ib_back.setOnClickListener(this);
        forgetpsw_btn_resetPassword.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgetpsw_ib_back:
                onBackPressed();
                break;
            case R.id.forgetpsw_tv_getPhoneCode:

                break;
            case R.id.forgetpsw_btn_resetPassword:

                break;
        }
    }
}
