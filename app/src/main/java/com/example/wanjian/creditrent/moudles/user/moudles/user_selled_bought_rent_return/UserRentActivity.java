package com.example.wanjian.creditrent.moudles.user.moudles.user_selled_bought_rent_return;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
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

public class UserRentActivity extends BaseActivity {

    private List<KindsBean> kindsBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    private EasyRefreshLayout easyRefreshLayout;
    private UserSelledBaseAdapter userSelledBaseAdapter;

    int page = 1;
    int TYPE_GOOD_RENT = 2;
    int rentNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_selled);

        initViews();


        getSelledGoods(page,TYPE_GOOD_RENT);

        recyclerView = (RecyclerView) findViewById(R.id.user_selled_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        easyRefreshLayout = (EasyRefreshLayout) findViewById(R.id.user_selled_easyrefreshlayout);
        easyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                getSelledGoods(page,TYPE_GOOD_RENT);
                easyRefreshLayout.loadMoreComplete();
                ToastUtil.show("加载完毕");
            }

            @Override
            public void onRefreshing() {
                kindsBeen.clear();
                page = 1;
                getSelledGoods(1,TYPE_GOOD_RENT);
                easyRefreshLayout.refreshComplete();
                ToastUtil.show("刷新完毕");
            }
        });

    }

    private void initViews() {
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),0);

        TextView content = (TextView) findViewById(R.id.user_selled_toolbar_content);
        content.setText("已出租的物品");

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
                        for (int i=0;i<value.size();i++){
                            kindsBeen.add(value.get(i));
                        }
                        userSelledBaseAdapter = new UserSelledBaseAdapter(getBaseContext(),R.layout.fragment_user_selled_item,kindsBeen);
                        recyclerView.setAdapter(userSelledBaseAdapter);
                        page ++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show("暂无更多数据");
                        saveRentNumber();
                    }

                    @Override
                    public void onComplete() {
                        saveRentNumber();
                        userSelledBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                startIntentActivity(UserRentActivity.this,new GoodsDetailinformationActivity(),"GoodId",kindsBeen.get(position).getGoodsid());
                            }
                        });
                    }
                });
    }

    private void saveRentNumber() {
        if (!kindsBeen.isEmpty()){
            rentNumber = userSelledBaseAdapter.getItemCount();
            ACache.getDefault().put(C.RENTNUMBER,rentNumber);
        }
    }


}
