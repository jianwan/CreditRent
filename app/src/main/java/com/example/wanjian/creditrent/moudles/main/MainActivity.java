package com.example.wanjian.creditrent.moudles.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.moudles.homepage.HomePageFragment;
import com.example.wanjian.creditrent.moudles.homepage.SearchActivity;
import com.example.wanjian.creditrent.moudles.kinds.KindsFragment;
import com.example.wanjian.creditrent.moudles.user.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private final String TAG=MainActivity.class.getSimpleName();
    private ViewPager mainViewpager;
    private HomePageFragment homepagerFragment;
    private KindsFragment kindsFragment;
    private UserFragment userFragment;
    private MainViewpagerAdapter mainViewpagerAdapter;
    private ImageView toolbarSearchview,ivOne,ivTwo,ivThree;
    private TextView tvOne,tvTwo,tvThree,tv_title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initToolbar();

    }

    private void initView() {
        mainViewpager = (ViewPager) findViewById(R.id.activity_main_viewPager);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        userFragment = new UserFragment();
        kindsFragment=new KindsFragment();
        homepagerFragment = new HomePageFragment();
        fragmentList.add(homepagerFragment);
        fragmentList.add(kindsFragment);
        fragmentList.add(userFragment);
        mainViewpagerAdapter = new MainViewpagerAdapter(getSupportFragmentManager(),fragmentList);
        mainViewpager.setOffscreenPageLimit(3);  //设置预加载
        mainViewpager.setAdapter(mainViewpagerAdapter);
        toolbarSearchview=(ImageView)findViewById(R.id.toolbar_searchview);
        ivOne = (ImageView) findViewById(R.id.linear1_iv);
        ivTwo = (ImageView) findViewById(R.id.linear2_iv);
        ivThree = (ImageView) findViewById(R.id.linear3_iv);
        tvOne = (TextView) findViewById(R.id.linear1_tv);
        tvThree = (TextView) findViewById(R.id.linear3_tv);
        tvTwo = (TextView) findViewById(R.id.linear2_tv);
        tv_title = (TextView) findViewById(R.id.toolbar_title);

        mainViewpager.setCurrentItem(0);  //设置当前选中的页面。
        //初始化txtView的颜色
        tvOne.setTextColor(this.getResources().getColor(R.color.main_toolbar));
        tvTwo.setTextColor(this.getResources().getColor(R.color.textColor));
        tvThree.setTextColor(this.getResources().getColor(R.color.textColor));

        toolbarSearchview.setOnClickListener(this);
        ivOne.setOnClickListener(this);
        ivTwo.setOnClickListener(this);
        ivThree.setOnClickListener(this);
        tvOne.setOnClickListener(this);
        tvTwo.setOnClickListener(this);
        tvThree.setOnClickListener(this);

        mainViewpager.setOnPageChangeListener(this);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            tv_title.setText("首页");
            toolbarSearchview.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear1_iv:
            case R.id.linear1_tv:
                mainViewpager.setCurrentItem(0);
                tv_title.setText("首页");
                toolbarSearchview.setVisibility(View.VISIBLE);
                break;
            case R.id.linear2_iv:
            case R.id.linear2_tv:
                mainViewpager.setCurrentItem(1);
                tv_title.setText("物品分类");
                toolbarSearchview.setVisibility(View.GONE);
                break;
            case R.id.linear3_iv:
            case R.id.linear3_tv:
                mainViewpager.setCurrentItem(2);
                tv_title.setText("个人中心");
                toolbarSearchview.setVisibility(View.GONE);
                break;
            case R.id.toolbar_searchview:
                startIntentActivity(this, new SearchActivity());
                break;
            default:
                break;
        }
    }


    //ViewPager
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void onPageSelected(int position) {
        switch (position){
            case 0:
                toolbarSearchview.setVisibility(View.VISIBLE);
                tv_title.setText("首页");
                ivOne.setImageResource(R.mipmap.home_seclect);
                ivTwo.setImageResource(R.mipmap.service_unseclect);
                ivThree.setImageResource(R.mipmap.my_unseclect);
                tvOne.setTextColor(this.getResources().getColor(R.color.main_toolbar));
                tvTwo.setTextColor(this.getResources().getColor(R.color.textColor));
                tvThree.setTextColor(this.getResources().getColor(R.color.textColor));
                break;
            case 1:
                toolbarSearchview.setVisibility(View.GONE);
                tv_title.setText("物品分类");
                ivOne.setImageResource(R.mipmap.home_unseclect);
                ivTwo.setImageResource(R.mipmap.service_seclect);
                ivThree.setImageResource(R.mipmap.my_unseclect);
                tvOne.setTextColor(this.getResources().getColor(R.color.textColor));
                tvTwo.setTextColor(this.getResources().getColor(R.color.main_toolbar));
                tvThree.setTextColor(this.getResources().getColor(R.color.textColor));
                break;
            case 2:
                toolbarSearchview.setVisibility(View.GONE);
                tv_title.setText("个人中心");
                ivOne.setImageResource(R.mipmap.home_unseclect);
                ivTwo.setImageResource(R.mipmap.service_unseclect);
                ivThree.setImageResource(R.mipmap.my_seclect);
                tvOne.setTextColor(this.getResources().getColor(R.color.textColor));
                tvTwo.setTextColor(this.getResources().getColor(R.color.textColor));
                tvThree.setTextColor(this.getResources().getColor(R.color.main_toolbar));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
