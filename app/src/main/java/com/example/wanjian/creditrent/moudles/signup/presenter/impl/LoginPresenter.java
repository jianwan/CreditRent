package com.example.wanjian.creditrent.moudles.signup.presenter.impl;


import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.moudles.signup.presenter.ILoginPresenter;
import com.example.wanjian.creditrent.moudles.signup.view.ILoginView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * Created by wanjian on 2017/11/1
 */
public class LoginPresenter extends BaseActivity implements ILoginPresenter {

    ILoginView iLoginView;

    public LoginPresenter(ILoginView view){
        this.iLoginView=view;
    }

    @Override
    public void userLogin(String phone, String password) {
        RetrofitNewSingleton.getInstance()
                .userLogin(phone,password)
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        iLoginView.setBtUnClickable();
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(String value) {
                        iLoginView.toast(value);
                        PLog.d(value);
                        iLoginView.saveInformation();
                        iLoginView.loginIntent();
                        finish();
                    }
                    @Override
                    public void onError(Throwable e) {
                        iLoginView.showErr(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });

//未知代码
//        RetrofitNewSingleton.getApiService().userLoginData(phone, password)
//                .filter(new Predicate<Result<String>>() {
//                    @Override
//                    public boolean test(Result<String> stringResult) throws Exception {
//                        return stringResult.code==1;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Function<Result<String>, String>() {
//                    @Override
//                    public String apply(Result<String> strResult) throws Exception {
//                        return strResult.data;
//                    }
//                }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//            }
//            @Override
//            public void onNext(String value) {
//                ACache.getDefault().put(C.USER_ID, value);
////                RefreshEvent refreshEvent = new RefreshEvent();
////                refreshEvent.setUserId(Integer.parseInt(value));
////                RxBus.getDefault().post(refreshEvent);
//                ACache.getDefault().put("user_id",value);
//                PLog.d("user_id",value);
//            }
//            @Override
//            public void onError(Throwable e) {
//                PLog.d("123456","error");
//            }
//            @Override
//            public void onComplete() {
//            }
//        });

    }

// //TODO:修改后，待测试
//    @Override
//    public void userLogin(String phone, String password) {
//
//        Observer<String> observer1=new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//            }
//            @Override
//            public void onNext(String value) {
//                iLoginView.toast(value);
//                PLog.d(value);
//                iLoginView.saveInformation();
//                iLoginView.loginIntent();
//                finish();
//            }
//            @Override
//            public void onError(Throwable e) {
//                iLoginView.showErr(e);
//            }
//            @Override
//            public void onComplete() {
//            }
//        };
//        mLoginModel.userLogin(observer1,phone,password);
//
//
//        Observer<String >observer2=new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//            }
//            @Override
//            public void onNext(String value) {
//                ACache.getDefault().put(C.USER_ID, value);
//                RefreshEvent refreshEvent = new RefreshEvent();
//                refreshEvent.setUserId(Integer.parseInt(value));
//                RxBus.getDefault().post(refreshEvent);
//                ACache.getDefault().put("user_id",value);
//                PLog.d("user_id",value);
//            }
//            @Override
//            public void onError(Throwable e) {
//            }
//            @Override
//            public void onComplete() {
//            }
//        };
//        mLoginModel.userLoginData(observer2,phone,password);
//    }


}
