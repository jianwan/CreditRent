package com.example.wanjian.creditrent.moudles.user.moudles.user_selled_bought_rent_return;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.homepage.kinds.KindsBean;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.GoodsDetailinformationActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/11/8.
 * 我卖出去的物品
 */

public class UserBoughtActivity extends BaseActivity {

    private List<KindsBean> kindBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    private EasyRefreshLayout easyRefreshLayout;
    private UserSelledBaseAdapter userSelledBaseAdapter;

    int page = 1;
    int TYPE_GOOD_BOUGHT = 4;

    ImageView back;

    Boolean isLoadMore = false;       //是否是加载更多
    Boolean isRefresh = false;        //是否是刷新
    Boolean isFirstLoad = true;        //是否是第一次加载数据

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharedPreferencesUtil.getIsLogin()){
            setContentView(R.layout.fragment_user_selled);

            initView();

            getSelledGoods(page,TYPE_GOOD_BOUGHT);

            recyclerView = (RecyclerView) findViewById(R.id.user_selled_recyclerview);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            easyRefreshLayout = (EasyRefreshLayout) findViewById(R.id.user_selled_easyrefreshlayout);
            easyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
                @Override
                public void onLoadMore() {
                    isLoadMore = true;
                    getSelledGoods(page,TYPE_GOOD_BOUGHT);
                }

                @Override
                public void onRefreshing() {
                    if (!kindBeen.isEmpty()){
                        userSelledBaseAdapter.getData().clear();
                    }
                    isRefresh = true;
                    page = 1;
                    getSelledGoods(1,TYPE_GOOD_BOUGHT);
                }
            });

        }else {
            setContentView(R.layout.fragment_user_nologin);
            back = (ImageView) findViewById(R.id.user_nologin_back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }



    }

    private void initView() {
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),0);

        TextView content = (TextView) findViewById(R.id.user_selled_toolbar_content);
        content.setText("买到的物品");

        back = (ImageView) findViewById(R.id.user_selled_toolbar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getSelledGoods(int intpage, int type_good_selled) {
        RetrofitNewSingleton.getInstance()
                .getUserGoodsByType(intpage,type_good_selled)
                .subscribe(new Observer<ArrayList<KindsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<KindsBean> value) {

                        if (isRefresh){
                            userSelledBaseAdapter.replaceData(value);
                            page ++ ;
                        }


                        if (isFirstLoad){
                            userSelledBaseAdapter = new UserSelledBaseAdapter(getBaseContext(),R.layout.fragment_user_selled_item,value);
                            recyclerView.setAdapter(userSelledBaseAdapter);
                            page ++;
                        }

                        if (isLoadMore){
                            userSelledBaseAdapter.getData().addAll(value);
                            userSelledBaseAdapter.notifyDataSetChanged();
                            page ++;
                        }

                        if (!value.isEmpty()){
                            kindBeen.addAll(value);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        easyRefreshLayout.loadMoreComplete();
                        easyRefreshLayout.refreshComplete();
                        isLoadMore = false;
                        isRefresh = false;
                        isFirstLoad = false;
                        ToastUtil.show("暂无更多数据");
                    }

                    @Override
                    public void onComplete() {

                        if (isLoadMore){
                            easyRefreshLayout.loadMoreComplete();
                            isLoadMore = false;
                            ToastUtil.show("数据加载完毕");
                        }

                        if (isRefresh){
                            easyRefreshLayout.refreshComplete();
                            isRefresh = false;
                            ToastUtil.show("刷新完毕");
                        }

                        if (isFirstLoad){
                            isFirstLoad = false;
                            ToastUtil.show("数据加载完毕");
                        }


                        userSelledBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                startIntentActivity(UserBoughtActivity.this,new GoodsDetailinformationActivity(),"GoodId",userSelledBaseAdapter.getItem(position).getGoodsid());
                            }
                        });
                    }
                });
    }
}
