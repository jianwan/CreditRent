package com.example.wanjian.creditrent.moudles.user.moudles.user_collection;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.GoodsDetailinformationActivity;
import com.jaeger.library.StatusBarUtil;

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
            StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);

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
                    }

                    @Override
                    public void onError(Throwable e) {
//                        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                    }

                    @Override
                    public void onComplete() {
                        userCollectionAdapter =new UserCollectionAdapter(R.layout.fragment_user_collected_item,userCollectionBean);

                        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(userCollectionAdapter);
                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
                        itemTouchHelper.attachToRecyclerView(recyclerView);

                        // 开启滑动删除
                        userCollectionAdapter.enableSwipeItem();
                        userCollectionAdapter.setOnItemSwipeListener(onItemSwipeListener);
                        recyclerView.setAdapter(userCollectionAdapter);

                        userCollectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                startIntentActivity(UserCollectedActivity.this,new GoodsDetailinformationActivity(),"GoodId",userCollectionBean.get(position).getGoodsid());
                            }
                        });

                        ToastUtil.show("数据加载完毕");
                    }

                });

    }



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

    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            RetrofitNewSingleton.getInstance()
                    .goodCollectionCancel(Integer.parseInt(userCollectionBean.get(pos).getGoodsid()))
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String value) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            ToastUtil.show("删除成功");
                        }
                    });
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

        }
    };

}
