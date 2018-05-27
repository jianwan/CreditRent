package com.example.wanjian.creditrent.moudles.user.moudles.user_collection;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/11/8.
 * 我收藏的物品
 */

public class UserCollectedActivity extends BaseActivity implements View.OnClickListener {


    private List<UserCollectionBean> userCollectionBean = new ArrayList<>();
    private RecyclerView recyclerView;
    UserCollectionAdapter userCollectionAdapter;


    private SwipeRefreshLayout swipeRefreshLayout;

    ImageView back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPreferencesUtil.getIsLogin()){
            setContentView(R.layout.fragment_user_collected);

            initViews();
            //获取收藏物品列表

            recyclerView=(RecyclerView)findViewById(R.id.collect_recyclerview);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            getCollection();


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



    private void getCollection() {

        //TODO: 此处需要优化，比如无数据时候也提示
        RetrofitNewSingleton.getInstance()
                .getGoodsCollection()
                .subscribe(new Observer<ArrayList<UserCollectionBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ArrayList<UserCollectionBean> value) {
                        for (int i=0;i<value.size();i++){
                            userCollectionBean.add(value.get(i));
                        }
                        userCollectionAdapter =new UserCollectionAdapter(R.layout.fragment_user_collected_item,userCollectionBean);
                        recyclerView.setAdapter(userCollectionAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {
//                        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                    }

                    @Override
                    public void onComplete() {
                        ToastUtil.show("数据加载完毕");
                    }
                });

    }


    //下拉刷新数据
//    private void refreshData() {
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.homepager_SwipeRefreshLayout);
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        getCollection();
//                    }
//                });
//            }
//        });
//
//    }

    private void initViews() {
        back = (ImageView) findViewById(R.id.user_settings_collection_toolbar_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_settings_collection_toolbar_back:
                onBackPressed();
                break;
        }
    }

}
