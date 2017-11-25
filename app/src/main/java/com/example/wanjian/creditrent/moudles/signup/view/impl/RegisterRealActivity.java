package com.example.wanjian.creditrent.moudles.signup.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.signup.AgreementActivity;
import com.example.wanjian.creditrent.moudles.signup.presenter.IRegisterRealPresenter;
import com.example.wanjian.creditrent.moudles.signup.presenter.impl.RegisterRealPresenter;
import com.example.wanjian.creditrent.moudles.signup.view.IRegisterRealView;



/**
 * Created by wanjian on 2017/10/30.
 */

public class RegisterRealActivity extends BaseActivity implements View.OnClickListener,IRegisterRealView {

    private final String TAG = RegisterRealActivity.class.getSimpleName();

    IRegisterRealPresenter iRegisterRealPresenter;

    EditText registerreal_et_username,registerreal_et_password,registerreal_et_password_again;
    CheckBox checkBox;
    TextView agreement;
    Button registerreal_btn_register;
    ImageButton registerreal_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_register_information);

        initView();

    }

    private void initView() {
        registerreal_et_username=(EditText)findViewById(R.id.registerreal_et_username);
        registerreal_et_password=(EditText)findViewById(R.id.registerreal_et_password);
        registerreal_et_password_again=(EditText)findViewById(R.id.registerreal_et_password_again);
        checkBox=(CheckBox)findViewById(R.id.check_box);
        agreement=(TextView)findViewById(R.id.agreement);
        registerreal_btn_register=(Button)findViewById(R.id.registerreal_btn_register);
        registerreal_btn_back=(ImageButton)findViewById(R.id.registerreal_btn_back);

        iRegisterRealPresenter=new RegisterRealPresenter(this);

        agreement.setOnClickListener(this);
        registerreal_btn_register.setOnClickListener(this);
        registerreal_btn_back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerreal_btn_back:
                onBackPressed();
                break;
            case R.id.agreement:
                Intent intentToAgreementActivity=new Intent(RegisterRealActivity.this, AgreementActivity.class);
                startActivity(intentToAgreementActivity);
                break;
            case R.id.registerreal_btn_register:
                if(registerreal_et_username.getText().toString().equals("")||registerreal_et_password.getText().toString().equals("")
                        ||registerreal_et_password_again.getText().toString().equals("")){
                    ToastUtil.show("输入不能为空");
                }else {
                    int usernameNumber=Integer.valueOf(registerreal_et_username.getText().toString());
                    Log.d(TAG,usernameNumber+"");
                    //判断学号是否合法
                    if (usernameNumber>=2014000000&&usernameNumber<=2017999999){
                        if (registerreal_et_password.getText().toString().equals(registerreal_et_password_again.getText().toString())){
                            //信租协议
                            if (checkBox.isChecked()){
                                ToastUtil.show("成功，准备登陆");
                                String phoneNumber=C.PHONR_NUMBER;
                                iRegisterRealPresenter.register(registerreal_et_username.getText().toString(),
                                        phoneNumber,registerreal_et_password_again.getText().toString());
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


    @Override
    public void saveInformation() {
        //缓存用户username，password
        ACache.getDefault().put(C.USER_NAME,registerreal_et_username.getText().toString());
        ACache.getDefault().put(C.PASSWORD,registerreal_et_password.getText().toString());
    }

    @Override
    public void showErr(Throwable e) {
        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
    }

}
