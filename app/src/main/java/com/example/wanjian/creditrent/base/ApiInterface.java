package com.example.wanjian.creditrent.base;


import com.example.wanjian.creditrent.moudles.chat.order.HandleOrderBean;
import com.example.wanjian.creditrent.moudles.chat.order.OrderBean;
import com.example.wanjian.creditrent.moudles.chat.rentcar.RentcarBean;
import com.example.wanjian.creditrent.moudles.homepage.UploadGoodsBean;
import com.example.wanjian.creditrent.moudles.homepage.kinds.KindsBean;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.GoodsDetailinformationBean;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.HomepagerGoodsList;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.MakeOrderBean;
import com.example.wanjian.creditrent.moudles.user.UserBean;
import com.example.wanjian.creditrent.moudles.user.moudles.user_collection.UserCollectionBean;

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
    @Multipart
    @POST("uploadrenzhengimg.php")
    Observable<Result<Object>> verifyPic(@Part MultipartBody.Part myfile);


    //登录遇到问题
    @FormUrlEncoded
    @POST("logoutwrong.php")
    Observable<Result<String>> logOutWithError(
            @Field("phone") String phone, @Field("password") String password);


    //展现首页物品列表
    @GET("showgoodsbypage.php")
    Observable<Results<ArrayList<HomepagerGoodsList>>> getHomepagerGoods(@Query("page") Integer page);


    //上传物品
    @FormUrlEncoded
    @POST("uploadgoods.php")
    Observable<Results<UploadGoodsBean>> uploadGoods(
            @Field("goodsname") String goodsname, @Field("typename") String typename, @Field("ershousell") Integer ershowsell,
             @Field("descrition") String description, @Field("chuzumoney") Double chuzumoney);

    //上传物品图片
    @Multipart
    @POST("uploadgoodsimg.php")
    Observable<Result<Object>> uploadGoodspic(
            @Part("goodsid") Integer goodid, @Part("imgnumber") Integer imgnumber,
            @Part MultipartBody.Part goodsimg);


    //展现物品详细信息
    @FormUrlEncoded
    @POST("showgoodsdetail.php")
    Observable<Results<GoodsDetailinformationBean>> getGoodDetialInformation(@Field("goodsid") String goodsid);

    //收藏物品
    @FormUrlEncoded
    @POST("usershoucanggoods.php")
    Observable<Result<Object>> goodCollection(@Field("goodsid") Integer goodid);

    //取消收藏
    @FormUrlEncoded
    @POST("userShouCangDeleteGoods.php")
    Observable<Result<Object>> goodCollectionCancel(@Field("goodsid") Integer goodid);

    //展现收藏页列表
    @GET("usershoucanggoodsshow.php")
    Observable<Results<ArrayList<UserCollectionBean>>> getGoodsCollection();

    //加入信租车
    @FormUrlEncoded
    @POST("userxinzuchegoods.php")
    Observable<Result<Object>> addGoodToRentCar(@Field("goodsid") Integer goodid);

    //展现信租车
    @GET("userxinzuchegoodsshow.php")
    Observable<Results<ArrayList<RentcarBean>>> getRentCarGoods();

    //加入信租车
    @FormUrlEncoded
    @POST("userXinZuCheDeleteGoods.php")
    Observable<Result<Object>> deleteGoodFromRentCar(@Field("goodsid") Integer goodid);

    //根据物品类型返回物品列表
    @FormUrlEncoded
    @POST("showbytypelist.php")
    Observable<Results<ArrayList<KindsBean>>> getGoodsByType(@Field("page") Integer page,@Field("type") Integer type);

    //我上传的物品，包括上架、正在审核，下架
    @FormUrlEncoded
    @POST("aboutowngoods.php")
    Observable<Results<ArrayList<KindsBean>>> getUserGoodsByType(@Field("page") Integer page,@Field("showtype") Integer type);

    //物品上架
    @FormUrlEncoded
    @POST("goodsshangjiarequest.php ")
    Observable<Result<Object>> userGoodPublised(@Field("goodsid") Integer goodid);

    //物品下架
    @FormUrlEncoded
    @POST("goodsxiajiarequest.php")
    Observable<Result<Object>> userGoodUnpublised(@Field("goodsid") Integer goodid);


    //修改物品信息
    @FormUrlEncoded
    @POST("ImproveGoodsInfo.php")
    Observable<Result<UploadGoodsBean>> changeGoodInformation(
            @Field("goodsid") Integer goodsid, @Field("goodsname") String goodsname,
            @Field("typename") String typename, @Field("ershousell") Integer ershowsell,
            @Field("descrition") String description, @Field("chuzumoney") String chuzumoney);

    //发起交易申请
    @FormUrlEncoded
    @POST("jiaoyiapplication.php")
    Observable<Result<MakeOrderBean>> makeOrder(@Field("goodsid") Integer goodsid, @Field("address") String address);


    //展示向我发起的交易请求
    @FormUrlEncoded
    @POST("showOthersApplication.php")
    Observable<Results<ArrayList<OrderBean>>> getOrderToMe(@Field("a")  String a);


    //处理交易申请
    @FormUrlEncoded
    @POST("chuzuZheHandleJiaoYiShenQing.php")
    Observable<Result<HandleOrderBean>> handleOrder(@Field("jiaoyiid") Integer orderId, @Field("taidu") Integer handle);


    //查看自己的交易记录
    @FormUrlEncoded
    @POST("showOwnApplication.php")
    Observable<Results<ArrayList<OrderBean>>> getMyOrders(@Field("a")  String a);


    //物品搜索
    @FormUrlEncoded
    @POST("searchgoods.php")
    Observable<Results<ArrayList<RentcarBean>>> searchGoods(@Field("page") Integer goodid,@Field("key") String key);

}