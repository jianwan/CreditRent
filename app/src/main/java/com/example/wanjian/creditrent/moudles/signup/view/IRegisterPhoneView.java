package com.example.wanjian.creditrent.moudles.signup.view;

/**
 * Created by wanjian on 2017/11/2.
 */

public interface IRegisterPhoneView {

    //倒计时
    void changeObservableTimer();

    void showSendCodeSuccessed();

    void showErr(Throwable e);

}
