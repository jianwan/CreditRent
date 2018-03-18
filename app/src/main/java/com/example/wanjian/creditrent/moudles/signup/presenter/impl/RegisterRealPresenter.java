package com.example.wanjian.creditrent.moudles.signup.presenter.impl;


import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.signup.presenter.IRegisterRealPresenter;
import com.example.wanjian.creditrent.moudles.signup.view.IRegisterRealView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/11/1.
 */

public class RegisterRealPresenter extends BaseActivity implements IRegisterRealPresenter {

    IRegisterRealView iRegisterRealView;


    public RegisterRealPresenter(IRegisterRealView view){
        this.iRegisterRealView=view;
    }


    @Override
    public void register(String phone,String nickname,String password,String yanzhengma){
        RetrofitNewSingleton.getInstance()
                    .register(phone,nickname,password,yanzhengma)
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            PLog.d("register","onSubscribe"+d);

                        }

                        @Override
                        public void onNext(String value) {
                            PLog.d("register","onNext"+value);
                            iRegisterRealView.loginFinishIntent();
                            iRegisterRealView.saveInformation();
                            ToastUtil.show("注册成功，请登录");
                        }

                        @Override
                        public void onError(Throwable e) {
                            iRegisterRealView.showErr(e.getMessage());
                            PLog.d("register","onError"+e);
                            RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                        }

                        @Override
                        public void onComplete() {
                            PLog.d("register","onComplete");
                        }
                    });


    }


}
