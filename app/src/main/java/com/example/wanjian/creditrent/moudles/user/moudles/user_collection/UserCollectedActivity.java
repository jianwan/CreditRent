package com.example.wanjian.creditrent.moudles.user.moudles.user_collection;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.Results;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;

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

    ImageView back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_collected);


        initViews();

        //TODO: 等待接口修改
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.collect_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        UserCollectionAdapter userCollectionAdapter =new UserCollectionAdapter(userCollectionBean);
        recyclerView.setAdapter(userCollectionAdapter);

        getCollection();

    }

    private void getCollection() {
        RetrofitNewSingleton.getApiService()
                .getGoodsCollection()
                .subscribe(new Observer<Results<ArrayList<UserCollectionBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Results<ArrayList<UserCollectionBean>> value) {


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initViews() {
        back = (ImageView) findViewById(R.id.user_settings_collection_toolbar_back);
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
