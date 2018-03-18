package com.example.wanjian.creditrent.moudles.signup.view;

/**
 * Created by wanjian on 2017/12/13.
 */

public interface IForgetPasswordView   {

    void saveInformation();

    void showErr(Throwable e);

    void loginFinishIntent();

    void changeObservableTimer();

    void showSendCodeSuccessed();

    void showResetPasswordSuccessed();

}
