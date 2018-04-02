package com.example.wanjian.creditrent.moudles.signup.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.main.MainActivity;
import com.example.wanjian.creditrent.moudles.signup.presenter.ILoginPresenter;
import com.example.wanjian.creditrent.moudles.signup.presenter.impl.LoginPresenter;
import com.example.wanjian.creditrent.moudles.signup.view.ILoginView;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.cache.LCIMProfileCache;


/**
 * Created by wanjian on 2017/10/25.
 * 登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginView{

    EditText login_et_username,login_et_password;
    Button login_btn_login;
    TextView login_tv_forgetPassword,login_tv_register;

    ILoginPresenter iLoginPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        initView();

    }



    private void initView() {
        login_et_username=(EditText)findViewById(R.id.login_et_username);
        login_et_password=(EditText)findViewById(R.id.login_et_password);
        login_btn_login=(Button)findViewById(R.id.login_btn_login);
        login_tv_forgetPassword=(TextView)findViewById(R.id.login_tv_forgetPassword);
        login_tv_register=(TextView)findViewById(R.id.login_tv_register);

        iLoginPresenter=new LoginPresenter(this);

        login_btn_login.setOnClickListener(this);
        login_tv_forgetPassword.setOnClickListener(this);
        login_tv_register.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:
                if (!login_et_username.getText().toString().isEmpty()&&!login_et_password.getText().toString().isEmpty()){
                    iLoginPresenter.userLogin(login_et_username.getText().toString(),login_et_password.getText().toString());
                }else {
                    ToastUtil.show("输入不能为空");
                }
                break;
            case R.id.login_tv_forgetPassword:
                Intent intentToForgetPasswordActivity=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intentToForgetPasswordActivity);
                break;
            case R.id.login_tv_register:
                Intent intentToRegisterPhoneActivity=new Intent(LoginActivity.this,RegisterPhoneActivity.class);
                startActivity(intentToRegisterPhoneActivity);
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
        //缓存用户username，password
        ACache.getDefault().put(C.USER_NAME,login_et_username.getText().toString());
        ACache.getDefault().put(C.PASSWORD,login_et_password.getText().toString());
        SharedPreferencesUtil.setIsLogin(true);

        changeInformationOnLeancloud();
    }

    private void changeInformationOnLeancloud() {
        String username= ACache.getDefault().getAsString(C.USER_NAME);
        String nickname= ACache.getDefault().getAsString(C.NICKNAME);
        LCChatKitUser user = new LCChatKitUser(username, nickname,"http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg");
        LCIMProfileCache.getInstance().cacheUser(user);


        AVUser.logInInBackground(username, nickname, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                ToastUtil.show("leancloud 登录成功");
            }
        });
    }


    @Override
    public void showErr(String error) {
//        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
        ToastUtil.show(error);
    }

    @Override
    public void toast(String s) {
        ToastUtil.show("登录成功");
    }

    @Override
    public void setBtUnClickable() {
        login_btn_login.setClickable(false);
    }

}
