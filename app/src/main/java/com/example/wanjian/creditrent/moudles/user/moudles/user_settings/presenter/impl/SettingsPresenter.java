package com.example.wanjian.creditrent.moudles.user.moudles.user_settings.presenter.impl;

import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.user.moudles.user_settings.presenter.ISettingsPresenter;
import com.example.wanjian.creditrent.moudles.user.moudles.user_settings.view.IUserSettings;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wanjian on 2017/12/12.
 */

public class SettingsPresenter extends BaseActivity implements ISettingsPresenter {


    IUserSettings iUserSettings;

    public SettingsPresenter(IUserSettings iUserSettings){
        this.iUserSettings=iUserSettings;
    }

    @Override
    public void loginOut(String noting) {

        PLog.d("loginOut");
        RetrofitNewSingleton.getInstance()
                .loginOut(noting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        PLog.d("loginOut","onSubscribe"+d);
                    }

                    @Override
                    public void onNext(String value) {
                        PLog.d("loginOut","onNext"+value);
                        ACache.getDefault().put(C.USER_NAME,"");
                        ACache.getDefault().put(C.PASSWORD,"");
                        iUserSettings.showLoginOutSuccessed();
                        iUserSettings.loginOutIntent();
                        ToastUtil.show(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        PLog.d("loginOut","onError"+e);
                        ToastUtil.show(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        PLog.d("loginOut","onComplete");
                    }

                });

    }

}
