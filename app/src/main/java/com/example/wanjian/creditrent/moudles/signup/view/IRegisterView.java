package com.example.wanjian.creditrent.moudles.signup.view;

/**
 * Created by wanjian on 2017/10/30.
 */

public interface IRegisterView {

    //倒计时
    void changeObservableTimer();

    void saveInformation();

    void showSendCodeSuccessed();

    void showErr(Throwable e);
}
