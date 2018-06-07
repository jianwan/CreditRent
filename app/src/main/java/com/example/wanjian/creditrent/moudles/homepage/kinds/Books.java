package com.example.wanjian.creditrent.moudles.homepage.kinds;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.GoodsDetailinformationActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/12/1.
 */

public class Books extends BaseActivity implements View.OnClickListener{

    private List<KindsBean> kindBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    private EasyRefreshLayout easyRefreshLayout;
    BasekindsAdapter basekindsAdapter;

    Toolbar toolbar;
    TextView content;
    ImageView back;

    int type;
    int page = 1;


    Boolean isLoadMore = false;       //是否是加载更多
    Boolean isRefresh = false;        //是否是刷新
    Boolean isFirstLoad = true;        //是否是第一次加载数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepager_kinds);


        Bundle bundle = this.getIntent().getExtras();
        type = bundle.getInt("type");
        ToastUtil.show(type+" ");

        initViews();
        initRecyclerview();

        getGoodsFromWeb(page,type);

    }



    private void initRecyclerview() {
        recyclerView=(RecyclerView)findViewById(R.id.homepager_kinds_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.homepager_kinds_toolbar);
        content = (TextView) findViewById(R.id.homepager_kinds_toolbar_content);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            if (type == 1){
                content.setText("图书");
            }else if (type == 2){
                content.setText("音像");
            }else if (type == 3){
                content.setText("箱包");
            }else if (type == 4){
                content.setText("数码");
            }else if (type == 5){
                content.setText("衣物");
            }else if (type == 6){
                content.setText("出行");
            }else if (type == 7){
                content.setText("娱乐");
            }else if (type == 8){
                content.setText("其他");
            }

            easyRefreshLayout = (EasyRefreshLayout) findViewById(R.id.homepager_kinds_easyrefreshlayout);
            easyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
                @Override
                public void onLoadMore() {
                    isLoadMore = true;
                    getGoodsFromWeb(page,type);
                }

                @Override
                public void onRefreshing() {
                    if (!kindBeen.isEmpty()){
                        basekindsAdapter.getData().clear();
                    }
                    isRefresh = true;
                    page = 1;
                    getGoodsFromWeb(page,type);
                }
            });
        }



        back = (ImageView) findViewById(R.id.homepager_kinds_toolbar_back);
        back.setOnClickListener(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);
    }

    private void getGoodsFromWeb(int intpage,int inttype) {
        RetrofitNewSingleton.getInstance()
                .getGoodsByType(intpage,inttype)
                .subscribe(new Observer<ArrayList<KindsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<KindsBean> value) {

                        if (isRefresh){
                            basekindsAdapter.replaceData(value);
                            page ++ ;
                        }


                        if (isFirstLoad){
                            basekindsAdapter = new BasekindsAdapter(getBaseContext(),R.layout.fragment_homepager_kinds_item,value);
                            recyclerView.setAdapter(basekindsAdapter);
                            page ++;
                        }


                        if (isLoadMore){
                            basekindsAdapter.getData().addAll(value);
                            basekindsAdapter.notifyDataSetChanged();
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



                        basekindsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                startIntentActivity(Books.this,new GoodsDetailinformationActivity(),"GoodId",basekindsAdapter.getItem(position).getGoodsid());
                            }
                        });
                        ToastUtil.show("数据加载完毕");
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.homepager_kinds_toolbar_back:
                onBackPressed();
                break;
        }
    }


}
