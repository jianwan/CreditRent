package com.example.wanjian.creditrent.moudles.signup.presenter;
/**
 * Created by wanjian on 2017/11/1.
 */
public interface IRegisterPresenter {
    //发送验证码
    void sendPhoneCode(String phone);
    //绑定手机号的注册
    void register(String username,String phone, String code,String password);

}
