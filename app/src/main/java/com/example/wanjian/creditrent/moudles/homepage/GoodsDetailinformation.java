package com.example.wanjian.creditrent.moudles.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.GoodsDetailinformationBean;
import com.example.wanjian.creditrent.moudles.signup.view.impl.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/12/2.
 */

public class GoodsDetailinformation extends BaseActivity implements OnItemClickListener,View.OnClickListener {


    TextView goodName,goodRenter,goodPrice,goodIsSell;
    Button contactToRenter,addToCart,rent;
    ImageView back;
    ConvenientBanner convenientBanner;
    private List<String> networkImages;
    private String[] images;
    String goodId;
    Boolean isCollected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepager_recyclerview_goodsinformation);


        Bundle bundle = this.getIntent().getExtras();
        goodId = bundle.getString("GoodId");
        ToastUtil.show(goodId);

        initViews();
        initToolbar();

        getGoodDetialInformation(goodId);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gooddetialinformation_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void initViews() {
        goodName = (TextView) findViewById(R.id.goodname);
        goodRenter = (TextView) findViewById(R.id.good_renter);
        goodPrice = (TextView) findViewById(R.id.good_price);
        goodIsSell = (TextView) findViewById(R.id.good_sell);
        contactToRenter = (Button) findViewById(R.id.goodsdetialinformation__btn_contact);
        addToCart = (Button) findViewById(R.id.goodsdetialinformation__btn_add);
        rent = (Button) findViewById(R.id.goodsdetialinformation__btn_rent);
        back = (ImageView) findViewById(R.id.goodsdetialinformation_back);

        convenientBanner = (ConvenientBanner)findViewById(R.id.goodsdetialinformation_convenientbanner);
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.goodsdetialinformation__toolbar);
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
                        goodName.setText(value.getGoodsname());
                        goodRenter.setText(value.getOwnerphone());
                        goodPrice.setText(value.getChuzu_money());
                        goodIsSell.setText(value.getErshousell());
                        images = new String[]{value.getGoods_img1(),value.getGoods_img2(),value.getGoods_img3()};
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

                break;
            case R.id.goodsdetialinformation__btn_add:

                break;
            case R.id.goodsdetialinformation__btn_rent:

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = Integer.parseInt(goodId);
        switch (item.getItemId()){
            case R.id.toolbar_collect:
                if (SharedPreferencesUtil.getIsLogin()){
                    if (!goodId.isEmpty()){
                        item.setIcon(R.drawable.collected);
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
//                                        item.setIcon(R.drawable.collected);
                                        ToastUtil.show("收藏成功");
                                    }
                                });
                    }else {
                        ToastUtil.show("收藏出错");
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
