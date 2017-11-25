package com.example.wanjian.creditrent.moudles.signup.presenter.impl;

import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.moudles.signup.presenter.IRegisterPhonePresenter;
import com.example.wanjian.creditrent.moudles.signup.view.IRegisterPhoneView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/11/2.
 */

public class RegisterPhonePresenter extends BaseActivity implements IRegisterPhonePresenter{


    IRegisterPhoneView iRegisterPhoneView;

    @Override
    public void sendPhoneCode(String phone) {
        RetrofitNewSingleton.getInstance()
                .getPhoneCode(phone)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        PLog.d("register1","onSubscribe"+d);
                    }
                    @Override
                    public void onNext(String value) {
                        iRegisterPhoneView.changeObservableTimer();
                        PLog.d("register1","onNext"+value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        iRegisterPhoneView.showErr(e);
                        PLog.d("register2","onError"+e);
                    }

                    @Override
                    public void onComplete() {
                        iRegisterPhoneView.showSendCodeSuccessed();
                        PLog.d("register2","onComplete");
                    }
                }) ;
    }

    @Override
    public void checkPhoneCode(String phone, String code) {
        RetrofitNewSingleton.getInstance()
                .checkPhoneCode(phone,code)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        ACache.getDefault().put(C.PHONR_NUMBER,phone);
                    }
                });
    }


}
