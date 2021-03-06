package com.example.wanjian.creditrent.base;


import android.content.Context;

import com.example.wanjian.creditrent.CreditRent_Application;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.common.RxUtil.RxUtils;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.chat.order.OrderBean;
import com.example.wanjian.creditrent.moudles.chat.rentcar.RentcarBean;
import com.example.wanjian.creditrent.moudles.homepage.UploadGoodsBean;
import com.example.wanjian.creditrent.moudles.homepage.kinds.KindsBean;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.GoodsDetailinformationBean;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.HomepagerGoodsList;
import com.example.wanjian.creditrent.moudles.signup.cookie.CookiesManager;
import com.example.wanjian.creditrent.moudles.user.UserBean;
import com.example.wanjian.creditrent.moudles.user.moudles.user_collection.UserCollectionBean;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by Hugo
 */
// TODO: 16/5/22 新封装好的网络库,推荐将网络请求统一写在该类,activity 直接调用方法即可 (讨论) --> 还需要封装好缓存的逻辑
public class RetrofitNewSingleton {

    private static ApiInterface apiService = null;
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;

    private static Context context ;


    private RetrofitNewSingleton() {
        initOkHttp();
        initRetrofit();
        apiService = retrofit.create(ApiInterface.class);
    }

    public static RetrofitNewSingleton getInstance() {
        return new RetrofitNewSingleton();
    }

    private static class SingletonHolder {
        private static final RetrofitNewSingleton INSTANCE = new RetrofitNewSingleton();
    }

    public static ApiInterface getApiService() {
        if (apiService == null) {
            throw new NullPointerException("get apiService must be called after init");
        }
        return apiService;
    }

    private static void initOkHttp() {
        // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
        //设置retrofit拦截器，打印出请求体
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);




        CookiesManager cookiesManager = new CookiesManager();

        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(CreditRent_Application.getContext()));

        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(interceptor)
                .cookieJar(cookieJar)
                .retryOnConnectionFailure(true)//断网自动重连
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

    }



    private static void initRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(new NobodyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static void disposeFailureInfo(Throwable t, Context context) {
        if (context != null) {
            if (t.toString().contains("GaiException")
                    || t.toString().contains("SocketTimeoutException")
                    || t.toString().contains("UnknownHostException")) {
                ToastUtil.showLong(R.string.no_network);
            } else if (t.toString().contains("ConnectException")) {
                ToastUtil.showLong(R.string.fail_connet);
            } else if (t.getMessage().equals(C.UNLOGIN)) {
                ToastUtil.showLong(C.UNLOGIN);
            } else {
                ToastUtil.show(t.getMessage());
                PLog.w(t.toString());
            }
        }
    }


//所有的API接口返回
//    /**
//     * 登陆注册 忘记密码
//     */
//    public Observable<String> userLogin(String name, String password) {
//        return apiService.userLogin(name, password)
//                .compose(RxUtils.rxSchedulerHelper())
//                .compose(RxUtils.handleResultToMsg());
//    }
//
//    public Observable<String> userLoginData(String name, String password) {
//        return apiService.userLoginData(name, password).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//
//    public Observable<String> registerApp1(String phone) {
//        return apiService.registerApp1(phone).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
//    }
//
//    public Observable<String> registerApp2(String phone, String code) {
//        return apiService.registerApp2(phone,code).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
//    }
//
//
//    public Observable<String> registerApp3(String phone, String name, String psw) {
//        return apiService.registerApp3(phone,name, psw).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
//    }
//
//    public Observable<String> findPassword1(String phone) {
//        return apiService.findPassword1(phone).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
//    }
//
//    public Observable<String> findPassword2(String phone, String code, String psw) {
//        return apiService.findPassword2(phone,code, psw).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
//    }
//
//    public Observable<String> signOut() {
//        return apiService.signOut().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
//    }
//
//    public Observable<String> getToken() {
//        return apiService.getToken().compose(RxUtils.rxSchedulerHelper()).compose( RxUtils.handleResultToMsg());
//
//    }
//    public  Observable<ArrayList<Tutorinfo>> getTutorList(int page) {
//        return apiService.getTutorList(page).compose(RxUtils.rxSchedulerHelper()).compose( RxUtils.handleResult());
//
//    }
//
//    /*public Observable<ArrayList<ChooseTutorModel>> getTechnologyTutor() {
//        return apiService.getTechnologyOrNotList(1).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//
//    public Observable<ArrayList<ChooseTutorModel>> getNoTechnologyTutor() {
//        return apiService.getTechnologyOrNotList(0).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }*/
//
//    // homepage 主界面+校招咨询详情
//    public Observable<ArrayList<XzInformationData>> getHomepageData(int page) {
//        return apiService.getHomepageData(page).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//
//    public Observable<Integer> addStar(int articleId, int userId){
//        return apiService.addStar(articleId, userId).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//
//    public Observable<Integer> removeStar(int articleId, int userId){
//        return apiService.removeStar(articleId, userId).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//
//
//    // 服务信息
//    public Observable<ServiceInfo> getServiceInfoData(int page) {
//        return apiService.getServiceInfoData(page).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//
//    /**
//     * 个人中心
//     * */
//    public Observable<ArrayList<SchoolInfo>> getAllCampus() {
//        return apiService.getSchools().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//
//    public Observable<VersionModel> getVersion() {
//        return apiService.getVersion().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//
//    public Observable<String> addUserBasicMsg(String tel,
//                                              String realName,
//                                              int sex,
//                                              String schoolName,
//                                              String academyName,
//                                              String major,
//                                              String career) {
//        return apiService.setUserBasicInfo(tel,
//                realName, sex, schoolName, academyName, major, career,
//                1).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//
//    }
//
//    public Observable<UserBasicMsg> getUserBasicMsg(String tel) {
//        return apiService.getUserBasicInfo(tel, 2).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//
//    public Observable<String> saveImageUrl(String imageUrl, String tel) {
//        return apiService.saveImageUrl(tel, imageUrl, 1).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
//    }
//
//
//    public Observable<ArrayList<XzInformationData>> getCollectArticle(int page, int userId){
//        return apiService.getCollectArticle(page, userId).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//    public Observable<ArrayList<ChooseTutorModel>> getTechnologyTutor() {
//        return apiService.getTechnologyOrNotList(1).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }
//
//    public Observable<ArrayList<ChooseTutorModel>> getNoTechnologyTutor() {
//        return apiService.getTechnologyOrNotList(0).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
//    }

    /**
     * 登陆注册 忘记密码
     */

    //获取验证码
    public Observable<String> getPhoneCode(String phone) {
        return apiService.getPhoneCode(phone).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }


    //注册
    public Observable<String> register(String phone,String nickname,String password,String yanzhengma) {
        return apiService.register(phone,nickname,password,yanzhengma).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //登录
    public Observable<String> userLogin( String name, String password) {
        return apiService.userLogin(name, password)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResultToMsg());
    }


    //退出登录
    public Observable<String> loginOut(String noting) {
        return apiService.loginOut(noting).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }


    //找回密码：获取验证码
    public Observable<String> findPasswordCode(String phone) {
        return apiService.findPasswordCode(phone).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //找回密码：重置密码
    public Observable<String> findPassword(String phone,String yanzhengma, String password) {
        return apiService.findPassword(phone,yanzhengma, password).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }


    //用户信息详情页，获取信息
    public Observable<UserBean> getUserInformation(String phone) {
        return apiService.getUserInformation(phone).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    //上传头像
    public Observable<String> uploadImgFile(MultipartBody.Part file) {
        return apiService.uploadImgFile(file).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //修改用户信息
    public Observable<String> changeUserInformation(String phone,String sex,String birthday,String school,String declaration) {
        return apiService.changeUserInformation(phone,sex,birthday,school,declaration).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //实名认证
    public Observable<String> verify(String name, String phone,String studentId, String verifyCode) {
        return apiService.verify(name,phone,studentId,verifyCode).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //实名认证一卡通图片上传
    public Observable<String> verifyPic(MultipartBody.Part file) {
        return apiService.verifyPic(file).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //登录遇到问题，退出登录
    public Observable<String> logOutWithError(String name, String password) {
        return apiService.logOutWithError(name, password).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }


    //展现首页物品列表
    public Observable<ArrayList<HomepagerGoodsList>> getHomepagerGoods(Integer page) {
        return apiService.getHomepagerGoods(page).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }


    //上传物品
    public Observable<UploadGoodsBean> uploadGoods(String goodsname, String typename, Integer ershousell,
                                                   String description, Double chuzumoney) {
        return apiService.uploadGoods(goodsname,typename,ershousell,description,chuzumoney)
                .compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    //上传物品图片
    public Observable<String> uploadGoodspic(Integer goodid, Integer imgnumber, MultipartBody.Part goodsimg) {
        return apiService.uploadGoodspic(goodid,imgnumber,goodsimg).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }


    //展现首页物品详细信息
    public Observable<GoodsDetailinformationBean> getGoodDetialInformation(String goodsid) {
        return apiService.getGoodDetialInformation(goodsid).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }


    //收藏物品
    public Observable<String> goodCollection(Integer goodsId) {
        return apiService.goodCollection(goodsId).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //取消收藏
    public Observable<String> goodCollectionCancel(Integer goodsId) {
        return apiService.goodCollectionCancel(goodsId).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //物品收藏列表
    public Observable<ArrayList<UserCollectionBean>> getGoodsCollection() {
        return apiService.getGoodsCollection().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    //加入信租车
    public Observable<String> addGoodToRentCar(Integer goodsId) {
        return apiService.addGoodToRentCar(goodsId).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //展现信租车
    public Observable<ArrayList<RentcarBean>> getRentCarGoods() {
        return apiService.getRentCarGoods().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    //加入信租车
    public Observable<String> deleteGoodFromRentCar(Integer goodsId) {
        return apiService.deleteGoodFromRentCar(goodsId).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }


    //根据物品类型返回物品列表
    public Observable<ArrayList<KindsBean>> getGoodsByType(Integer page, Integer type) {
        return apiService.getGoodsByType(page, type).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }


    //我的页面物品
    public Observable<ArrayList<KindsBean>> getUserGoodsByType(Integer page, Integer type) {
        return apiService.getUserGoodsByType(page, type).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    //物品下架
    public Observable<String> userGoodUnpublised(Integer goodsId) {
        return apiService.userGoodUnpublised(goodsId).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //物品上架
    public Observable<String> userGoodPublised(Integer goodsId) {
        return apiService.userGoodPublised(goodsId).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }


    //修改物品信息
    public Observable<String> changeGoodInformation(Integer goodsid,String goodsname, String typename, Integer ershousell,
                                                   String description, String chuzumoney) {
        return apiService.changeGoodInformation(goodsid,goodsname,typename,ershousell,description,chuzumoney)
                .compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }


    //发起交易申请
    public Observable<String> makeOrder(Integer goodsid, String address) {
        return apiService.makeOrder(goodsid, address).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }


    //展示向我发起的交易请求
    public Observable<ArrayList<OrderBean>> getOrderToMe(String a) {
        return apiService.getOrderToMe(a).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    //处理交易申请
    public Observable<String> handleOrder(Integer  orderId, Integer handle) {
        return apiService.handleOrder(orderId, handle).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResultToMsg());
    }

    //展示向我发起的交易请求
    public Observable<ArrayList<OrderBean>> getMyOrders(String a) {
        return apiService.getMyOrders(a).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }


    //关键字搜索物品
    public Observable<ArrayList<RentcarBean>> searchGoods(Integer page,String key) {
        return apiService.searchGoods(page,key).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

}
