package com.example.wanjian.creditrent.moudles.signup.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.ObservableTimer;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.signup.presenter.IForgetPasswordPresenter;
import com.example.wanjian.creditrent.moudles.signup.presenter.impl.ForgetPasswordPresenter;
import com.example.wanjian.creditrent.moudles.signup.view.IForgetPasswordView;

/**
 * Created by wanjian on 2017/10/30.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener,IForgetPasswordView{


    private final String TAG = ForgetPasswordActivity.class.getSimpleName();

    EditText forgetpsw_et_phonemumber,forgetpsw_et_phoneCode,forgetpsw_et_newPassword;
    TextView forgetpsw_tv_getPhoneCode;
    Button forgetpsw_btn_resetPassword;
    ImageButton forgetpsw_ib_back;

    IForgetPasswordPresenter iForgetPasswordPresenter;

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

        iForgetPasswordPresenter=new ForgetPasswordPresenter(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgetpsw_ib_back:
                onBackPressed();
                break;
            case R.id.forgetpsw_tv_getPhoneCode:
                if (forgetpsw_et_phonemumber.getText().toString().length()==11){
                    iForgetPasswordPresenter.getFhoneCode(forgetpsw_et_phonemumber.getText().toString());
                }else {
                    ToastUtil.show("请输入正确的手机号");
                }
                break;
            case R.id.forgetpsw_btn_resetPassword:
                if (!forgetpsw_et_phonemumber.getText().toString().isEmpty()
                        &&!forgetpsw_et_phoneCode.getText().toString().isEmpty()
                        &&!forgetpsw_et_newPassword.getText().toString().isEmpty()){
                    iForgetPasswordPresenter.resetPassword(forgetpsw_et_phonemumber.getText().toString(),
                            forgetpsw_et_phoneCode.getText().toString(),
                            forgetpsw_et_newPassword.getText().toString());
                }else {
                    ToastUtil.show("输入不能为空");
                }
                break;
        }
    }

    @Override
    public void saveInformation() {
        //缓存用户username，password
        ACache.getDefault().put(C.USER_NAME,forgetpsw_et_phonemumber.getText().toString());
        ACache.getDefault().put(C.PASSWORD,forgetpsw_et_newPassword.getText().toString());
    }

    @Override
    public void showErr(Throwable e) {
        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
    }

    @Override
    public void loginFinishIntent() {
        Intent intent=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void changeObservableTimer() {
        new ObservableTimer(forgetpsw_tv_getPhoneCode, TAG).startTimer();
    }

    @Override
    public void showSendCodeSuccessed() {
        ToastUtil.show("验证码发送成功");
    }

    @Override
    public void showResetPasswordSuccessed() {
        ToastUtil.show("修改密码成功，请及时登录");
    }


}
