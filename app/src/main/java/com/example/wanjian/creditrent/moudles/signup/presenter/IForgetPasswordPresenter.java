package com.example.wanjian.creditrent.moudles.signup.presenter;

/**
 * Created by wanjian on 2017/12/13.
 */

public interface IForgetPasswordPresenter {

    void getFhoneCode(String phone);

    void resetPassword(String phone,String phoneCode,String password);
}
