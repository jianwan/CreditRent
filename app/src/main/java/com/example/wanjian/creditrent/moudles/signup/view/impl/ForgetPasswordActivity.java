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


    EditText et_phonemumber,et_phoneCode,et_newPassword;
    TextView tv_getPhoneCode;
    Button btn_resetPassword;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_forgetpassword);

        initView();
    }

    private void initView() {
        et_phonemumber=(EditText)findViewById(R.id.et_phonenumber);
        et_phoneCode=(EditText)findViewById(R.id.et_phoneCode);
        et_newPassword=(EditText)findViewById(R.id.et_newPassword);
        tv_getPhoneCode=(TextView)findViewById(R.id.tv_getPhoneCode);
        btn_back=(ImageButton)findViewById(R.id.btn_back);
        btn_resetPassword=(Button)findViewById(R.id.btn_resetPassword);

        tv_getPhoneCode.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_resetPassword.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.tv_getPhoneCode:

                break;
            case R.id.btn_resetPassword:

                break;
        }
    }
}
