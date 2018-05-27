package cn.leancloud.chatkit.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.R;

/**
 * Created by 17631 on 2018/5/25.
 */

public class CrentCarActivity extends AppCompatActivity{

    private List<CrentCarBean> crentCarBean = new ArrayList<>();
    private RecyclerView recyclerview;
    private ImageView pic;
    private TextView goodname,goodstatus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.crent_car);

        initVews();
        initRecyclerview();

        getCrentCarGoods();

    }



    private void initVews() {
        pic = (ImageView) findViewById(R.id.crentcar_item_pic);
        goodname = (TextView) findViewById(R.id.crentcar_item_goodname);
        goodstatus = (TextView) findViewById(R.id.crentcar_item_goodstatus);
    }

    private void initRecyclerview() {
        recyclerview = (RecyclerView) findViewById(R.id.crent_car_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
//        CrentCarAdapter crentCarAdapter = new CrentCarAdapter(crentCarBean);
//        recyclerview.setAdapter(crentCarAdapter);
    }


    private void getCrentCarGoods() {

    }

}
