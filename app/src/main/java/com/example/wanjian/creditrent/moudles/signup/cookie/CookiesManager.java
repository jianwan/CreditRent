package com.example.wanjian.creditrent.moudles.signup.cookie;

import com.example.wanjian.creditrent.CreditRent_Application;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by wanjian on 2017/8/17.
 */


   //自动管理Cookies
public class CookiesManager implements CookieJar {

    private final PersistentCookieStore cookieStore = new PersistentCookieStore(CreditRent_Application.getContext());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);

                //测试代码
//                PLog.d("TAG1",cookies.toString());
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);

        //测试代码
//        try {
//            PLog.d("TAG2",cookies.get(0)+" ");
//        }catch (Exception e){
//            PLog.d(e.toString());
//        }

        return cookies;
    }

}
