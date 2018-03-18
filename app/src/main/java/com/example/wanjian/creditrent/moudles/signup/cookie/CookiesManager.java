package com.example.wanjian.creditrent.moudles.signup.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import static com.example.wanjian.creditrent.common.CreditRent_Context.getApplicationContext;

/**
 * Created by wanjian on 2017/8/17.
 */


   //自动管理Cookies
public class CookiesManager implements CookieJar {

    private final PersistentCookieStore cookieStore = new PersistentCookieStore(getApplicationContext());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }

}
