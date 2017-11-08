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
import com.example.wanjian.creditrent.moudles.signup.presenter.IRegisterPhonePresenter;
import com.example.wanjian.creditrent.moudles.signup.presenter.impl.RegisterPhonePresenter;
import com.example.wanjian.creditrent.moudles.signup.view.IRegisterPhoneView;

/**
 * Created by wanjian on 2017/11/2.
 */

public class RegisterPhoneActivity extends BaseActivity implements IRegisterPhoneView, View.OnClickListener{

    private final String TAG = RegisterPhoneActivity.class.getSimpleName();
    IRegisterPhonePresenter iRegisterPhonePresenter;

    EditText et_phonenumber,et_phoneCode;
    TextView tv_getPhoneCode;
    Button btn_nextStep;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_register_phone);

        initView();

    }

    private void initView() {
        iRegisterPhonePresenter=new RegisterPhonePresenter();

        et_phonenumber=(EditText)findViewById(R.id.et_phonenumber);
        et_phoneCode=(EditText)findViewById(R.id.et_phoneCode);
        tv_getPhoneCode=(TextView)findViewById(R.id.tv_getPhoneCode);
        btn_nextStep=(Button)findViewById(R.id.btn_nextStep);
        btn_back=(ImageButton)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(this);
        tv_getPhoneCode.setOnClickListener(this);
        btn_nextStep.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.tv_getPhoneCode:
                if (tv_getPhoneCode.getText().length()==11){
                    iRegisterPhonePresenter.sendPhoneCode(et_phonenumber.getText().toString());
                }else {
                    ToastUtil.show("请输入正确的手机号码！");
                }
                break;
            case R.id.btn_nextStep:
//                if (!et_phonenumber.getText().toString().isEmpty()&&!et_phoneCode.getText().toString().isEmpty()){
//                    iRegisterPhonePresenter.checkPhoneCode(et_phonenumber.getText().toString(),et_phoneCode.getText().toString());
//                    Intent intentToRegisterRealActivity=new Intent(RegisterPhoneActivity.this,RegisterRealActivity.class);
////                    intentToRegisterRealActivity.putExtra("PhoneNumber",et_phonenumber.getText().toString());
//                    ACache.getDefault().put(C.PHONR_NUMBER,et_phonenumber.getText().toString());
//                    startActivity(intentToRegisterRealActivity);
//                }

//                iRegisterPhonePresenter.checkPhoneCode(et_phonenumber.getText().toString(),et_phoneCode.getText().toString());
                Intent intentToRegisterRealActivity=new Intent(RegisterPhoneActivity.this,RegisterRealActivity.class);
//                    intentToRegisterRealActivity.putExtra("PhoneNumber",et_phonenumber.getText().toString());
                ACache.getDefault().put(C.PHONR_NUMBER,et_phonenumber.getText().toString());
                startActivity(intentToRegisterRealActivity);
                break;
        }
    }



    @Override
    public void showSendCodeSuccessed() {
        ToastUtil.show("验证码发送成功");
    }



    //倒计时
    @Override
    public void changeObservableTimer() {
        new ObservableTimer(tv_getPhoneCode, TAG).startTimer();
    }

    @Override
    public void showErr(Throwable e) {
        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
    }

}
