package com.example.wanjian.creditrent.moudles.user.moudles.user_settings.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.main.MainActivity;
import com.example.wanjian.creditrent.moudles.user.moudles.user_settings.presenter.ISettingsPresenter;
import com.example.wanjian.creditrent.moudles.user.moudles.user_settings.presenter.impl.SettingsPresenter;
import com.example.wanjian.creditrent.moudles.user.moudles.user_settings.view.IUserSettings;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by wanjian on 2017/11/8.
 */

public class UserSettingsActivity extends BaseActivity implements View.OnClickListener,IUserSettings {

    Button btn_loginout;
    ISettingsPresenter iSettingsPresenter;
    Boolean isLogin;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_settings);

        initViews();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);

    }

    private void initViews() {
        btn_loginout=(Button)findViewById(R.id.btn_loginout);
        btn_loginout.setOnClickListener(this);

        iSettingsPresenter=new SettingsPresenter(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_loginout:
                isLogin= SharedPreferencesUtil.getIsLogin();
                String username = ACache.getDefault().getAsString(C.USER_NAME);
                String password =ACache.getDefault().getAsString(C.PASSWORD);
                PLog.d("TAG","islogin"+isLogin.toString()+"username"+username+"password"+password);
                if (isLogin&&username != ""||password != ""){
                    String nothing=null;
                    iSettingsPresenter.loginOut(nothing);
                }else {
                    ToastUtil.show("你还未登录！");
                }
                break;
        }
    }

    @Override
    public void showLoginOutSuccessed() {
        ToastUtil.show("退出登录成功");
    }

    @Override
    public void loginOutIntent() {

        ACache.getDefault().put(C.USER_NAME,"");
        ACache.getDefault().put(C.PASSWORD,"");
        SharedPreferencesUtil.setIsLogin(false);

        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
