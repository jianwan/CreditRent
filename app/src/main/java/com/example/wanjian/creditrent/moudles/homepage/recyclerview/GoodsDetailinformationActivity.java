package com.example.wanjian.creditrent.moudles.homepage.recyclerview;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.signup.view.impl.LoginActivity;
import com.jaeger.library.StatusBarUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Arrays;
import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/12/2.
 */

public class GoodsDetailinformationActivity extends BaseActivity implements OnItemClickListener,View.OnClickListener {


    TextView goodName,gooddescription,goodRenter,goodPrice,goodIsSell;
    Integer isSave;
    Button contactToRenter,addToCart,rent;
    ImageView back;
    ConvenientBanner convenientBanner;
    private List<String> networkImages;
    private String[] images;
    String goodId;
    String userId;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepager_recyclerview_goodsinformation);


        Bundle bundle = this.getIntent().getExtras();
        goodId = bundle.getString("GoodId");
        ToastUtil.show(goodId);

        initViews();
        initToolbar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);

        getGoodDetialInformation(goodId);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gooddetialinformation_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void initViews() {
        goodName = (TextView) findViewById(R.id.goodname);
        gooddescription = (TextView) findViewById(R.id.gooddescription);
        goodRenter = (TextView) findViewById(R.id.good_renter);
        goodPrice = (TextView) findViewById(R.id.good_price);
        goodIsSell = (TextView) findViewById(R.id.good_sell);
        contactToRenter = (Button) findViewById(R.id.goodsdetialinformation__btn_contact);
        addToCart = (Button) findViewById(R.id.goodsdetialinformation__btn_add);
        rent = (Button) findViewById(R.id.goodsdetialinformation__btn_rent);
        back = (ImageView) findViewById(R.id.goodsdetialinformation_back);

        convenientBanner = (ConvenientBanner)findViewById(R.id.goodsdetialinformation_convenientbanner);
        contactToRenter.setOnClickListener(this);
        addToCart.setOnClickListener(this);
        rent.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.goodsdetialinformation__toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    //ConvenientBanner
    private void initConvenientBanner() {
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages)
                .setPointViewVisible(true)
                .startTurning(10000)
                .setOnItemClickListener(this)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }

    @Override
    public void onItemClick(int position) {

    }

    //NetworkImageHolderView
    private class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageResource(R.drawable.ic_sync_blue_grey_200_24dp);
            ImageLoader.getInstance().displayImage(data,imageView);
        }
    }



    //获取物品详细信息
    private void getGoodDetialInformation(String id) {
        RetrofitNewSingleton.getInstance()
                .getGoodDetialInformation(id)
                .subscribe(new Observer<GoodsDetailinformationBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(GoodsDetailinformationBean value) {
                        userId = value.getOwnerphone();
                        goodName.setText(value.getGoodsname());
                        gooddescription.setText(value.getGoods_description());
                        goodRenter.setText(value.getOwnerphone());
                        goodPrice.setText(value.getChuzu_money());
                        if (value.getErshousell().equals("0")){
                            goodIsSell.setText("出租");
                        }else {
                            goodIsSell.setText("出售");
                        }

                        images = new String[]{value.getGoods_img1(),value.getGoods_img2(),value.getGoods_img3()};
                        isSave = value.getIsSave();
                        if (isSave == 1){
                            toolbar.getMenu().getItem(0).setIcon(R.drawable.collected);
                        }else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                    }

                    @Override
                    public void onComplete() {
                        initConvenientBanner();
                    }
                });
    }


    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goodsdetialinformation_back:
                onBackPressed();
                break;
            case R.id.goodsdetialinformation__btn_contact:
                if (SharedPreferencesUtil.getIsLogin()){
                    LCChatKitUser lcChatKitUser =new LCChatKitUser(ACache.getDefault().getAsString(C.USER_NAME),
                            ACache.getDefault().getAsString(C.NICKNAME),"http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg");
                    LCChatKit.getInstance().open(lcChatKitUser.getUserId(), new AVIMClientCallback() {
                        @Override
                        public void done(AVIMClient avimClient, AVIMException e) {
                            if (null == e) {
                                Intent intent = new Intent(GoodsDetailinformationActivity.this, LCIMConversationActivity.class);
                                intent.putExtra(LCIMConstants.PEER_ID, userId);
                                startActivity(intent);
                            } else {
                                ToastUtil.show(e.toString());
                            }
                        }
                    });

                }else {
                    ToastUtil.show("请先登录后再试~");
                }

                break;
            case R.id.goodsdetialinformation__btn_add:
                addToCrentCar();
                break;
            case R.id.goodsdetialinformation__btn_rent:
                if (SharedPreferencesUtil.getIsLogin()){
                    makeOrder();
                }else {
                    startIntentActivity(this,new LoginActivity());
                    ToastUtil.show("请先登录后再试");
                }
                break;
        }
    }


    //发起交易
    private void makeOrder() {

        //显示dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.fragment_chat_makeorder_dialog, null);//获取自定义布局
        builder.setView(layout);
        builder.setTitle("确认发起交易");//设置标题内容
        //builder.setMessage("");//显示自定义布局内容


        //确认按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                TextView content = layout.findViewById(R.id.fragment_chat_makeorder_address);
                String address = content.getText().toString();

                if (!address.isEmpty()&&address != ""){
                    RetrofitNewSingleton.getInstance()
                            .makeOrder(Integer.parseInt(goodId), address)
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(String value) {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                                }

                                @Override
                                public void onComplete() {
                                    ToastUtil.show("交易发起成功,请及时联系物主进行交易~");
                                }
                            });
                }else {
                    ToastUtil.show("请填写好信息再进行交易");
                }

            }
        });
        //取消
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getApplication(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
        final AlertDialog dlg = builder.create();
        dlg.show();
    }

    //添加到信租车
    private void addToCrentCar() {
        Integer id = Integer.parseInt(goodId);
        if (SharedPreferencesUtil.getIsLogin()){
            RetrofitNewSingleton.getInstance()
                    .addGoodToRentCar(id)
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String value) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                        }

                        @Override
                        public void onComplete() {
                            ToastUtil.show("添加成功,请到信租车中查看~");
                        }
                    });

        }else {
            startIntentActivity(this,new LoginActivity());
            ToastUtil.show("请先登录后再试");
        }
    }


    //收藏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = Integer.parseInt(goodId);
        switch (item.getItemId()){
            case R.id.toolbar_collect:
                if (SharedPreferencesUtil.getIsLogin()){
                    if (!goodId.isEmpty()){
                        //根据是否服务器返回的收藏状态决定是增加收藏还是取消收藏
                        if (isSave == 1){
                            RetrofitNewSingleton.getInstance()
                                    .goodCollectionCancel(id)
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(String value) {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                                        }

                                        @Override
                                        public void onComplete() {
                                            toolbar.getMenu().getItem(0).setIcon(R.drawable.collect);
                                            isSave = 0;
                                            ToastUtil.show("取消收藏");
                                        }
                                    });
                        }else {
                            RetrofitNewSingleton.getInstance()
                                    .goodCollection(id)
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                        }

                                        @Override
                                        public void onNext(String value) {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                                        }

                                        @Override
                                        public void onComplete() {
                                            toolbar.getMenu().getItem(0).setIcon(R.drawable.collected);
                                            isSave = 1;
                                            ToastUtil.show("收藏成功");
                                        }
                                    });
                        }
                    }else {
                        ToastUtil.show("网络出错");
                    }
                }else {
                    startIntentActivity(this,new LoginActivity());
                    ToastUtil.show("请先登录后再试");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
