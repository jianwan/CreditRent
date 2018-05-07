package com.example.wanjian.creditrent.moudles.signup.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2018/4/15.
 * 登录遇到问题
 */

public class QuestionActivity extends BaseActivity implements View.OnClickListener{

    EditText question_et_username,question_et_password;
    Button question_btn_logout;

    String username,password;
    MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_question);

        initView();


    }

    private void initView() {
        question_et_username = (EditText) findViewById(R.id.question_et_username);
        question_et_password = (EditText) findViewById(R.id.question_et_password);
        question_btn_logout = (Button) findViewById(R.id.question_btn_logout);

        question_btn_logout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.question_btn_logout:
                if (!question_et_username.getText().toString().isEmpty()&&!question_et_password.getText().toString().isEmpty()){
                    logout(question_et_username.getText().toString(),question_et_password.getText().toString());
                    Log.d("TAG","username"+username+"password"+password);
                }else {
                    ToastUtil.show("输入不能为空");
                }
                break;
            default:
                break;
        }
    }


    //登录遇到问题强制退出
    private void logout(String username, String password) {
        RetrofitNewSingleton.getInstance()
                .logOutWithError(username,password)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {

                        //dialog
                        dialog = new MaterialDialog.Builder(QuestionActivity.this)
                                .content("正在退出")
                                .progress(true, 0)
                                .show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                    }

                    @Override
                    public void onComplete() {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();

                                Intent intent=new Intent(QuestionActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                ToastUtil.show("退出成功，请再次登录，如有问题，请及时联系客服");
                            }
                        }, 1000);

                    }
                });
    }

}
