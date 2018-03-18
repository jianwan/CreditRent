package com.example.wanjian.creditrent.moudles.user.moudles.user_publish;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjian on 2017/11/8.
 * 我的上架物品
 */

public class UserPublishedActivity extends BaseActivity implements View.OnClickListener{

    private List<UserPublishList> userPublishLists=new ArrayList<>();

    ImageView user_published_toolbar_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_published);

        init();
        initView();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.user_published_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        UserPublishAdapter userPublishAdapter=new UserPublishAdapter(userPublishLists);
        recyclerView.setAdapter(userPublishAdapter);
    }



    private void init() {

        for (int i=0;i<30;i++){
            UserPublishList userpublish=new UserPublishList(R.mipmap.ic_launcher,"数据结构","2017-11-15",""+i);
            userPublishLists.add(userpublish);
        }
    }

    private void initView() {
        user_published_toolbar_back=(ImageView)findViewById(R.id.user_published_toolbar_back);

        user_published_toolbar_back.setOnClickListener(this);
    }



    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_published_toolbar_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

}
