package com.example.wanjian.creditrent.base;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Soully on 2017/7/25.
 */
public  interface ApiInterface {

     String HOST="http://119.29.133.244/test/adminweb/";


    /** ————————————————登录注册—————————————————**/
    //获取验证码
    @FormUrlEncoded
    @POST("zhuctanz.php")
    Observable<Result<Object>> getPhoneCode(@Field("phone") String phone);



    //注册
    @FormUrlEncoded
    @POST("register.php")
    Observable<Result<Object>> register(@Field("phone") String phone,@Field("nickname") String nickname,@Field("password") String password,
                                        @Field("yanzhengma") String yanzhengma);

    //注册返回信息
    @FormUrlEncoded
    @POST("register.php")
    Observable<Result<String>> registerData(@Field("phone") String phone,@Field("nickname") String nickname,@Field("password") String password,
                                        @Field("yanzhengma") String yanzhengma);

    //登录接口
    @FormUrlEncoded
    @POST("login.php")
    Observable<Result<Object>> userLogin(
            @Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("app/user/login")
    Observable<Result<String>> userLoginData(
            @Field("phone") String phone, @Field("password") String password);


    //退出登录
    @FormUrlEncoded
    @POST("logout.php")
    Observable<Result<Object>> loginOut(@Field("none") String noting);


    //忘记密码  第一步:获取验证码
    @FormUrlEncoded
    @POST("zhaohtanz.php")
    Observable<Result<Object>> findPasswordCode(
            @Field("phone") String phone);

    //忘记密码  第二步：找回密码
    @FormUrlEncoded
    @POST("findmima.php")
    Observable<Result<Object>> findPassword(
            @Field("phone") String phone, @Field("yanzhengma") String yanzhengma, @Field("password") String password);


}