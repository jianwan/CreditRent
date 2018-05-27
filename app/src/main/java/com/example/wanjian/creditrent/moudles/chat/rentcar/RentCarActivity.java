package com.example.wanjian.creditrent.moudles.chat.rentcar;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.GoodsDetailinformationActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 17631 on 2018/5/26.
 */

public class RentCarActivity extends BaseActivity implements View.OnClickListener{


    private List<RentcarBean> rentcarBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    RentcarAdapter rentcarAdapter;

    Toolbar toolbar;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharedPreferencesUtil.getIsLogin()){
            setContentView(R.layout.fragment_chat_rentcar);

            back = (ImageView) findViewById(R.id.chat_rentcar_toolbar_back);
            back.setOnClickListener(this);

            recyclerView=(RecyclerView)findViewById(R.id.chat_rentcar_recyclerview);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            getGoodsFromWeb();


        }else {
            setContentView(R.layout.fragment_user_nologin);
            toolbar = (Toolbar) findViewById(R.id.user_nologin_toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle("信租车");

            back = (ImageView) findViewById(R.id.user_nologin_back);
            back.setOnClickListener(this);
        }

    }

    private void getGoodsFromWeb() {
        RetrofitNewSingleton.getInstance()
                .getRentCarGoods()
                .subscribe(new Observer<ArrayList<RentcarBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<RentcarBean> value) {
                        for (int i=0;i<value.size();i++){
                            rentcarBeen.add(value.get(i));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        rentcarAdapter =new RentcarAdapter(R.layout.fragment_chat_rentcar_item,rentcarBeen);

                        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(rentcarAdapter);
                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
                        itemTouchHelper.attachToRecyclerView(recyclerView);

                        // 开启滑动删除
                        rentcarAdapter.enableSwipeItem();
                        rentcarAdapter.setOnItemSwipeListener(onItemSwipeListener);
                        recyclerView.setAdapter(rentcarAdapter);

                        rentcarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                startIntentActivity(RentCarActivity.this,new GoodsDetailinformationActivity(),"GoodId",rentcarBeen.get(position).getGoodsid());
                                PLog.d("TAG1","rentcarada"+rentcarBeen.get(position).getGoodsid());
                            }
                        });
                        ToastUtil.show("数据加载完毕");
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_nologin_back:
            case R.id.chat_rentcar_toolbar_back:
                onBackPressed();
                break;
        }
    }


    //滑动删除
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
                    .deleteGoodFromRentCar(Integer.parseInt(rentcarBeen.get(pos).getGoodsid()))
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
                            ToastUtil.show("删除成功~");
                        }
                    });

        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

        }
    };


    
}
