package com.example.wanjian.creditrent.moudles.signup.presenter;

/**
 * Created by wanjian on 2017/11/2.
 */

public interface IRegisterPhonePresenter {

    //发送验证码
    void sendPhoneCode(String phone);

   void checkPhoneCode(String phone,String code);

}
