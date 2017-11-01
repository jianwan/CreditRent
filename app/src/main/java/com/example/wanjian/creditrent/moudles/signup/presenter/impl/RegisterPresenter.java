package com.example.wanjian.creditrent.moudles.signup.presenter.impl;


import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.moudles.signup.presenter.IRegisterPresenter;
import com.example.wanjian.creditrent.moudles.signup.view.IRegisterView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/11/1.
 */

public class RegisterPresenter extends BaseActivity implements IRegisterPresenter {

    IRegisterView iRegisterView;


    public RegisterPresenter(IRegisterView view){
        this.iRegisterView=view;
    }

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
                        iRegisterView.changeObservableTimer();
                        PLog.d("register1","onNext"+value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        iRegisterView.showErr(e);
                        PLog.d("register2","onError"+e);
                    }

                    @Override
                    public void onComplete() {
                        iRegisterView.showSendCodeSuccessed();
                        PLog.d("register2","onComplete");
                    }
                }) ;
    }

//TODO:修改后，待测试
//    @Override
//    public void register1(String phone) {
//
//        Observer<String>observer1=new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                PLog.d("register1","onSubscribe"+d);
//            }
//            @Override
//            public void onNext(String value) {
//                iSignupView.changeObservableTimer();
//                PLog.d("register1","onNext"+value);
//            }
//            @Override
//            public void onError(Throwable e) {
//                iSignupView.showErr(e);
//                PLog.d("register2","onError"+e);
//            }
//            @Override
//            public void onComplete() {
//                iSignupView.showSendCodeSuccessed();
//                PLog.d("register2","onComplete");
//            }
//        };
//        mSignupModel.registerApp1(observer1,phone);
//    }


    @Override
    public void register(String username,String phone, String code,String password){
        RetrofitNewSingleton.getInstance()
                    .register(username,phone,code,password)
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            PLog.d("register2","onSubscribe"+d);

                        }

                        @Override
                        public void onNext(String value) {
                            iRegisterView.saveInformation();
                            PLog.d("register2","onNext"+value);

                        }

                        @Override
                        public void onError(Throwable e) {
                            iRegisterView.showErr(e);
                            PLog.d("register2","onError"+e);
                        }

                        @Override
                        public void onComplete() {
                            PLog.d("register2","onComplete");
                        }
                    });
    }

//TODO:修改后，待测试
//    @Override
//    public void register2(String phone, String code) {
//        Observer<String>observer2=new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                PLog.d("register2","onSubscribe"+d);
//            }
//            @Override
//            public void onNext(String value) {
//                iSignupView.savePhone();
//                PLog.d("register2","onNext"+value);
//            }
//            @Override
//            public void onError(Throwable e) {
//                iSignupView.showErr(e);
//                PLog.d("register2","onError"+e);
//            }
//            @Override
//            public void onComplete() {
//                PLog.d("register2","onComplete");
//            }
//        };
//        mSignupModel.registerApp2(observer2,phone,code);
//    }


}
