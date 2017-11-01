package com.example.wanjian.creditrent.moudles.signup.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.main.MainActivity;
import com.example.wanjian.creditrent.moudles.signup.presenter.ILoginPresenter;
import com.example.wanjian.creditrent.moudles.signup.presenter.impl.LoginPresenter;
import com.example.wanjian.creditrent.moudles.signup.view.ILoginView;

/**
 * Created by wanjian on 2017/10/25.
 * 登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginView{

    EditText et_username,et_phonenumber,et_password;
    Button btn_login;
    TextView tv_forgetPassword, tv_register;

    ILoginPresenter iLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        initView();
    }

    private void initView() {
        et_username=(EditText)findViewById(R.id.et_username);
        et_phonenumber=(EditText)findViewById(R.id.et_phonenumber);
        et_password=(EditText)findViewById(R.id.et_password);
        btn_login=(Button)findViewById(R.id.btn_login);
        tv_forgetPassword=(TextView)findViewById(R.id.tv_forgetPassword);
        tv_register=(TextView)findViewById(R.id.tv_register);

        iLoginPresenter=new LoginPresenter(this);

        btn_login.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
        tv_register.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                iLoginPresenter.userLogin(et_username.getText().toString(),et_password.getText().toString());
                break;
            case R.id.tv_forgetPassword:
                Intent intentToForgetPasswordActivity=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intentToForgetPasswordActivity);
                break;
            case R.id.tv_register:
                Intent intentToRegisterActivity=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentToRegisterActivity);
                break;
        }
    }



    @Override
    public void loginIntent() {
        Intent intentToMainActivtiy=new Intent(LoginActivity.this, MainActivity.class);
        intentToMainActivtiy.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intentToMainActivtiy.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToMainActivtiy);
    }

    @Override
    public void saveInformation() {
        //缓存用户username，phonenumber，password
        ACache.getDefault().put(C.USER_NAME,et_username.getText().toString());
        ACache.getDefault().put(C.PHONR_NUMBER,et_phonenumber.getText().toString());
        ACache.getDefault().put(C.PASSWORD,et_password.getText().toString());
    }

    @Override
    public void showErr(Throwable e) {
        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
    }

    @Override
    public void toast(String s) {
        ToastUtil.show("登录成功");
    }

    @Override
    public void setBtUnClickable() {
        btn_login.setClickable(false);
    }
}
