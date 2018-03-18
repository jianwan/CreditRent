package com.example.wanjian.creditrent.moudles.signup.presenter.impl;

import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.signup.presenter.IForgetPasswordPresenter;
import com.example.wanjian.creditrent.moudles.signup.view.IForgetPasswordView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wanjian on 2017/12/13.
 */

public class ForgetPasswordPresenter extends BaseActivity implements IForgetPasswordPresenter {


    IForgetPasswordView iForgetPasswordView;

    public ForgetPasswordPresenter(IForgetPasswordView forgetPasswordView){
        this.iForgetPasswordView=forgetPasswordView;
    }



    @Override
    public void getFhoneCode(String phone) {
        RetrofitNewSingleton.getInstance()
                .findPasswordCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        PLog.d("getPhoneCode","onSubscribe"+d);
                    }

                    @Override
                    public void onNext(String value) {

                        iForgetPasswordView.showSendCodeSuccessed();
                        iForgetPasswordView.changeObservableTimer();
                        PLog.d("getPhoneCode","onNext"+value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        PLog.d("getPhoneCode","onError"+e);
                        ToastUtil.show(e.getMessage());
                        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                    }

                    @Override
                    public void onComplete() {
                        PLog.d("getPhoneCode","onComplete");
                    }
                });
    }



    @Override
    public void resetPassword(String phone, String phoneCode, String password) {
            RetrofitNewSingleton.getInstance()
                    .findPassword(phone,phoneCode,password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            PLog.d("resetPassword","onSubscribe"+d);
                        }

                        @Override
                        public void onNext(String value) {
                            PLog.d("resetPassword","onNext"+value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            PLog.d("resetPassword","onError"+e);
                            ToastUtil.show(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            PLog.d("resetPassword","onComplete");
                            iForgetPasswordView.saveInformation();
                            iForgetPasswordView.showResetPasswordSuccessed();
                            iForgetPasswordView.loginFinishIntent();
                        }
                    });
    }


}
