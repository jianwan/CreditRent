package com.example.wanjian.creditrent.moudles.signup.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.ObservableTimer;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.signup.AgreementActivity;
import com.example.wanjian.creditrent.moudles.signup.presenter.IRegisterPresenter;
import com.example.wanjian.creditrent.moudles.signup.presenter.impl.RegisterPresenter;
import com.example.wanjian.creditrent.moudles.signup.view.IRegisterView;


/**
 * Created by wanjian on 2017/10/30.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener,IRegisterView{

    private final String TAG = RegisterActivity.class.getSimpleName();

    IRegisterPresenter iSignupPresenter;

    EditText et_username,et_phonenumber,et_password,et_password_again;
    CheckBox checkBox;
    TextView agreement,getPhoneCode;
    Button btn_register;

    String username,phonenumber,password,password_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_register);

        initView();

    }

    private void initView() {
        et_username=(EditText)findViewById(R.id.et_username);
        et_phonenumber=(EditText)findViewById(R.id.et_phonenumber);
        getPhoneCode=(TextView)findViewById(R.id.getPhoneCode);
        et_password=(EditText)findViewById(R.id.et_password);
        et_password_again=(EditText)findViewById(R.id.et_password_again);
        checkBox=(CheckBox)findViewById(R.id.check_box);
        agreement=(TextView)findViewById(R.id.agreement);
        btn_register=(Button)findViewById(R.id.btn_register);

        iSignupPresenter=new RegisterPresenter(this);

        getPhoneCode.setOnClickListener(this);
        agreement.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agreement:
                Intent intentToAgreementActivity=new Intent(RegisterActivity.this, AgreementActivity.class);
                startActivity(intentToAgreementActivity);
                break;
            case R.id.getPhoneCode:
                iSignupPresenter.sendPhoneCode(et_phonenumber.getText().toString());
                break;
            case R.id.btn_register:
                if(et_username.getText().toString().equals("")||password.equals("")||password_again.equals("")||phonenumber.equals("")){
                    ToastUtil.show("输入不能为空");
                }else {
                    int usernameNumber=Integer.valueOf(username);
                    Log.d(TAG,usernameNumber+"");
                    //判断学号是否合法
                    if (usernameNumber>=2014000000&&usernameNumber<=2017999999){
                        if (et_password.getText().toString().equals(et_password_again.getText().toString())){
                            //信租协议
                            if (checkBox.isChecked()){
                                ToastUtil.show("成功，准备登陆");
                                iSignupPresenter.register(et_username.getText().toString(),et_phonenumber.getText().toString(),
                                        et_password.getText().toString(),et_password_again.getText().toString());
                            }else {
                                ToastUtil.show("请阅读并同意“信租协议”");
                            }
                        }else {
                            ToastUtil.show("两次输入的密码不一致，请重新输入");
                        }
                    }else {
                        ToastUtil.show("输入的学号不合法，请重新输入");
                    }
                }
                break;
        }
    }


    //倒计时
    @Override
    public void changeObservableTimer() {
        new ObservableTimer(getPhoneCode, TAG).startTimer();
    }

    @Override
    public void saveInformation() {
        //缓存用户username，phonenumber，password
        ACache.getDefault().put(C.USER_NAME,et_username.getText().toString());
        ACache.getDefault().put(C.PHONR_NUMBER,et_phonenumber.getText().toString());
        ACache.getDefault().put(C.PASSWORD,et_password.getText().toString());
    }

    @Override
    public void showSendCodeSuccessed() {
         ToastUtil.show("验证码发送成功");
    }

    @Override
    public void showErr(Throwable e) {
        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
    }


}
