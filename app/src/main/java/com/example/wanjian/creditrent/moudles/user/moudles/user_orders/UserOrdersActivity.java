package com.example.wanjian.creditrent.moudles.user.moudles.user_orders;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.chat.order.OrderBean;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 17631 on 2018/5/26.
 */

public class UserOrdersActivity extends BaseActivity implements View.OnClickListener{


    private List<OrderBean> orderBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserOrdersAdapter userOrdersAdapter;
    private EasyRefreshLayout easyRefreshLayout;

    Toolbar toolbar;
    ImageView back;

    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharedPreferencesUtil.getIsLogin()){
            setContentView(R.layout.fragment_chat_order);


            initViews();



            recyclerView=(RecyclerView)findViewById(R.id.chat_order_recyclerview);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            getGoodsFromWeb();

            easyRefreshLayout = (EasyRefreshLayout) findViewById(R.id.chat_order_easyrefreshlayout);
            easyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
                @Override
                public void onLoadMore() {
                    easyRefreshLayout.loadMoreComplete();
                    ToastUtil.show("加载完毕");
                }

                @Override
                public void onRefreshing() {
                    orderBeen.clear();
                    getGoodsFromWeb();
                    easyRefreshLayout.refreshComplete();
                    ToastUtil.show("刷新完毕");
                }
            });


        }else {
            setContentView(R.layout.fragment_user_nologin);
            toolbar = (Toolbar) findViewById(R.id.user_nologin_toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle("交易申请");
            back = (ImageView) findViewById(R.id.user_nologin_back);
            back.setOnClickListener(this);
            StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);
        }

    }

    private void initViews() {


        status = (TextView) findViewById(R.id.myorder_item_status);

        back = (ImageView) findViewById(R.id.chat_order_toolbar_back);
        back.setOnClickListener(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),0);
    }

    private void getGoodsFromWeb() {
        RetrofitNewSingleton.getInstance()
                .getMyOrders(null)
                .subscribe(new Observer<ArrayList<OrderBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<OrderBean> value) {
                        for (int i=0;i<value.size();i++){
                            orderBeen.add(value.get(i));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show("暂无更多数据");
                    }

                    @Override
                    public void onComplete() {
                        userOrdersAdapter =new UserOrdersAdapter(R.layout.fragment_user_myorder_item,orderBeen);
                        recyclerView.setAdapter(userOrdersAdapter);

//                        userOrdersAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                                startIntentActivity(UserOrdersActivity.this,new GoodsDetailinformationActivity(),"GoodId",orderBeen.get(position).getGoodsid());
//                            }
//                        });

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


}
