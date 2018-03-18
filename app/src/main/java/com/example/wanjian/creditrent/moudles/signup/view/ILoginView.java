package com.example.wanjian.creditrent.moudles.signup.view;

/**
 * Created by wanjian on 2017/10/30.
 */

public interface ILoginView {

    void loginIntent();

    void saveInformation();

    void showErr(String e) ;

    void toast(String s);

    void setBtUnClickable();
}
