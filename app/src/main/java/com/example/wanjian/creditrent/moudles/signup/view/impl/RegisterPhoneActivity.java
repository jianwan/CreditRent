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
import com.example.wanjian.creditrent.common.util.ObservableTimer;
import com.example.wanjian.creditrent.common.util.PLog;
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
    EditText register_et_phonenumber,register_et_phoneCode;
    TextView register_tv_getPhoneCode;
    Button register_btn_nextStep;
    ImageButton register_ib_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_register_phone);

        initView();

    }

    private void initView() {

        register_et_phonenumber=(EditText)findViewById(R.id.register_et_phonenumber);
        register_et_phoneCode=(EditText)findViewById(R.id.register_et_phoneCode);
        register_tv_getPhoneCode=(TextView)findViewById(R.id.register_tv_getPhoneCode);
        register_btn_nextStep=(Button)findViewById(R.id.register_btn_nextStep);
        register_ib_back=(ImageButton)findViewById(R.id.register_ib_back);

        iRegisterPhonePresenter=new RegisterPhonePresenter(this);

        register_ib_back.setOnClickListener(this);
        register_tv_getPhoneCode.setOnClickListener(this);
        register_btn_nextStep.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register_ib_back:
                onBackPressed();
                break;
            case R.id.register_tv_getPhoneCode:
                if (register_et_phonenumber.getText().toString().length()==11){
                    iRegisterPhonePresenter.sendPhoneCode(register_et_phonenumber.getText().toString());
                    PLog.d(register_et_phonenumber.getText().toString());
                }else {
                    ToastUtil.show("请输入正确的手机号码！");
                }
                break;
            case R.id.register_btn_nextStep:
                if (!register_et_phonenumber.getText().toString().isEmpty()&&!register_et_phoneCode.getText().toString().isEmpty()){

                    Intent intent=new Intent(RegisterPhoneActivity.this,RegisterRealActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("phoneNumber",register_et_phonenumber.getText().toString());
                    bundle.putString("phoneCode",register_et_phoneCode.getText().toString());
                    intent.putExtras(bundle);
                    PLog.d("TAG",register_et_phonenumber.getText().toString()+register_et_phoneCode.getText().toString());
                    startActivity(intent);

                }else {
                    ToastUtil.show("输入不能为空");
                }
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
        new ObservableTimer(register_tv_getPhoneCode).startTimer();
    }

    @Override
    public void showErr(Throwable e) {
//        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
        PLog.d(e.toString());
    }


}
