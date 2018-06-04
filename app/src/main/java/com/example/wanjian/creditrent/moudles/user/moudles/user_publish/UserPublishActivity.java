package com.example.wanjian.creditrent.moudles.user.moudles.user_publish;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.jaeger.library.StatusBarUtil;

import static com.example.wanjian.creditrent.R.id.user_publish_toolbar_back;


/**
 * Created by wanjian on 2017/11/8.
 * 我发布的物品，包括上架，审核，下架的物品
 */

public class UserPublishActivity extends BaseActivity implements View.OnClickListener{

    private UserPublishPageAdapter userPublishPageAdapter;

    ImageView back;

    int fragmentPos = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharedPreferencesUtil.getIsLogin()){

            setContentView(R.layout.fragment_user_publish);
            initView();


            TabLayout tabLayout = (TabLayout) findViewById(R.id.user_publish_tablayout);
            tabLayout.addTab(tabLayout.newTab().setText("已上架"));
            tabLayout.addTab(tabLayout.newTab().setText("审核中"));
            tabLayout.addTab(tabLayout.newTab().setText("已下架"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




            ViewPager viewPager = (ViewPager) findViewById(R.id.user_publish_viewpager);
            userPublishPageAdapter = new UserPublishPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(userPublishPageAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });



        }else {
            setContentView(R.layout.fragment_user_nologin);
            back = (ImageView) findViewById(R.id.user_nologin_back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            TextView content = (TextView) findViewById(R.id.user_nologin_content);
            content.setText("我发布的物品");
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        back =(ImageView)findViewById(user_publish_toolbar_back);
        back.setOnClickListener(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),0);
    }



    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case user_publish_toolbar_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

}
