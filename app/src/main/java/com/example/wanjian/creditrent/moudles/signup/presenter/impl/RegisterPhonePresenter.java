package com.example.wanjian.creditrent.moudles.signup.presenter.impl;

import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.signup.presenter.IRegisterPhonePresenter;
import com.example.wanjian.creditrent.moudles.signup.view.IRegisterPhoneView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/11/2.
 */

public class RegisterPhonePresenter extends BaseActivity implements IRegisterPhonePresenter{


    IRegisterPhoneView iRegisterPhoneView;

    public RegisterPhonePresenter(IRegisterPhoneView iRegisterPhoneView){
        this.iRegisterPhoneView=iRegisterPhoneView;
    }


    @Override
    public void sendPhoneCode(String phone) {
        RetrofitNewSingleton.getInstance()
                .getPhoneCode(phone)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        PLog.d("getPhoneCode","onSubscribe"+d);
                    }
                    @Override
                    public void onNext(String value) {
                        iRegisterPhoneView.showSendCodeSuccessed();
                        iRegisterPhoneView.changeObservableTimer();
                        PLog.d("getPhoneCode","onNext"+value);
                    }
                    @Override
                    public void onError(Throwable e) {
//                        iRegisterPhoneView.showErr(e);
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

//    @Override
//    public void checkPhoneCode(String phone, String code) {
//        RetrofitNewSingleton.getInstance()
//                .checkPhoneCode(phone,code)
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String value) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        iRegisterPhoneView.showErr(e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        iRegisterPhoneView.intentToRegisterReal();
//                        ACache.getDefault().put(C.PHONR_NUMBER,phone);
//                    }
//                });
//    }


}
