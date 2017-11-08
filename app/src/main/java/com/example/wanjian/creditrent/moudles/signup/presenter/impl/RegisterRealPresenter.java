package com.example.wanjian.creditrent.moudles.signup.presenter.impl;


import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.moudles.signup.presenter.IRegisterRealPresenter;
import com.example.wanjian.creditrent.moudles.signup.view.IRegisterRealView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/11/1.
 */

public class RegisterRealPresenter extends BaseActivity implements IRegisterRealPresenter {

    IRegisterRealView iRegisterView;

    public RegisterRealPresenter(IRegisterRealView view){
        this.iRegisterView=view;
    }


    @Override
    public void register(String username,String phone,String password){
        RetrofitNewSingleton.getInstance()
                    .register(username,phone,password)
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
