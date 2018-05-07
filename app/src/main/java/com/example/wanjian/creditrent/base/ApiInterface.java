package com.example.wanjian.creditrent.base;


import com.example.wanjian.creditrent.moudles.homepage.recyclerview.HomepagerGoodsList;
import com.example.wanjian.creditrent.moudles.user.UserBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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


    //TODO 接口待测

    //修改个人信息
    @FormUrlEncoded
    @POST("update.php")
    Observable<Result<UserBean>> changeUserInformation(
            @Field("nickname") String nickname,@Field("sex") String sex,@Field("birthday") String birthday,
            @Field("area") String school,@Field("qianming") String declaration);


    //展现个人信息
    @FormUrlEncoded
    @POST("showuserinfo.php")
    Observable<Results<UserBean>> getUserInformation(@Field("phone") String phone);


    //上传头像
    @POST("uploadimg.php")
    @Multipart
    Observable<Result<String>> uploadImgFile(@Part MultipartBody.Part myfile);


    //实名认证
    @FormUrlEncoded
    @POST("realname.php")
    Observable<Result<String>> verify(
            @Field("name") String name,@Field("phone") String phone,@Field("xuehao") String xuehao,
            @Field("tongyi") String verifyCode);

    //实名认证一卡通图片上传
    @FormUrlEncoded
    @POST("uploadrenzhengimg.php")
    Observable<Result<Object>> verifyPic(@Part MultipartBody.Part myfile);


    //登录遇到问题
    @FormUrlEncoded
    @POST("logoutwrong.php")
    Observable<Result<String>> logOutWithError(
            @Field("phone") String phone, @Field("password") String password);


    //展现个人信息
    @GET("showgoodsbypage.php")
    Observable<Results<ArrayList<HomepagerGoodsList>>> getHomepagerGoods(@Query("page") Integer page);


    //上传物品
    @FormUrlEncoded
    @POST("uploadgoods.php")
    Observable<Result<String>> uploadGoods(
            @Field("goodsname") String goodsname, @Field("typename") String typename, @Field("ershowsell") Integer ershowsell,
            @Field("ershousellmoney") Double ershousellmoney, @Field("descrition") String description, @Field("chuzumoney") Double chuzumoney);

    //上传物品图片
    @FormUrlEncoded
    @POST("uploadgoodsimg.php")
    Observable<Result<Object>> uploadGoodspic(
            @Field("goodid") Integer goodid, @Field("imgnumber") Integer imgnumber,@Part MultipartBody.Part myfile);


}