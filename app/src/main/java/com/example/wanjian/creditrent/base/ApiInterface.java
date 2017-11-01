package com.example.wanjian.creditrent.base;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Soully on 2017/7/25.
 */
public  interface ApiInterface {

            String HOST="http://www.lingshi321.com/tutor/";



    /** ————————————————登录注册—————————————————**/
    //获取验证码
    @FormUrlEncoded
    @POST("app/user/phoneCode")
    Observable<Result<Object>> getPhoneCode(@Field("phone") String phone);

    //注册
    @FormUrlEncoded
    @POST("app/user/register")
    Observable<Result<Object>> register(@Field("username") String username,@Field("phoneNumber") String phoneNumber,
                                        @Field("phoneCode") String phoneCode,@Field("password") String password);


    //登录接口
    @FormUrlEncoded
    @POST("app/user/login")
    Observable<Result<Object>> userLogin(
            @Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("app/user/login")
    Observable<Result<String>> userLoginData(
            @Field("phone") String phone, @Field("password") String password);


    //重置密码
    @FormUrlEncoded
    @POST("app/user/phoneCode")
    Observable<Result<Object>> findPassword1(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("app/user/pwdChange")
    Observable<Result<Object>> findPassword2(
            @Field("phone") String phone, @Field("code") String code, @Field("newPassword") String newPassword);
}