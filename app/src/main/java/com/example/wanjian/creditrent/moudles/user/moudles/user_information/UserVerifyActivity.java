package com.example.wanjian.creditrent.moudles.user.moudles.user_information;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by wanjian on 2018/4/7.
 */

public class UserVerifyActivity extends BaseActivity implements View.OnClickListener{

    private EditText user_verify_truename,user_verify_phone,user_verify_studentId,user_verify_code;
    private ImageView user_dl_verify_toolbar_back;
    private Button user_verify_submit;

    String truename,phone,studentId,verifyCode;


    MaterialDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detailinformation_verify);

        initViews();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);

    }

    private void initViews() {

        user_verify_truename = (EditText) findViewById(R.id.user_verify_truename);
        user_verify_phone = (EditText) findViewById(R.id.user_verify_phone);
        user_verify_studentId = (EditText) findViewById(R.id.user_verify_studentId);
        user_verify_code = (EditText) findViewById(R.id.user_verify_code);
        user_dl_verify_toolbar_back = (ImageView) findViewById(R.id.user_dl_verify_toolbar_back);
        user_verify_submit = (Button) findViewById(R.id.user_verify_submit);


        user_dl_verify_toolbar_back.setOnClickListener(this);
        user_verify_submit.setOnClickListener(this);
    }



    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        truename = user_verify_truename.getText().toString();
        phone = user_verify_phone.getText().toString();
        studentId = user_verify_studentId.getText().toString();
        verifyCode = user_verify_code.getText().toString();

        switch (v.getId()){
            case R.id.user_dl_verify_toolbar_back:
                onBackPressed();
                break;

            case R.id.user_verify_submit:
                if (!truename.isEmpty()&&!phone.isEmpty()&&!studentId.isEmpty()&&!verifyCode.isEmpty()){
                    verifyInformation(truename,phone,studentId,verifyCode);
                }else {
                    ToastUtil.show("请填好所有信息再提交验证");
                }
                break;
            default:
                break;
        }
    }


    //提交验证
    private void verifyInformation(String truename,String phone,String studentId,String verifyCode) {
        RetrofitNewSingleton.getInstance()
                .verify(truename,phone,studentId,verifyCode)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        //dialog
                        dialog = new MaterialDialog.Builder(UserVerifyActivity.this)
                                .content("正在提交信息")
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

                                Intent intent=new Intent(UserVerifyActivity.this, UserVerifyPicActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                ToastUtil.show("信息提交成功");
                            }
                        }, 1500);

                    }
                });

    }






}
