package com.example.wanjian.creditrent.moudles.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.chat.ChatFragment;
import com.example.wanjian.creditrent.moudles.chat.order.OrderBean;
import com.example.wanjian.creditrent.moudles.chat.order.OrdersActivity;
import com.example.wanjian.creditrent.moudles.chat.rentcar.RentCarActivity;
import com.example.wanjian.creditrent.moudles.homepage.HomePageFragment;
import com.example.wanjian.creditrent.moudles.homepage.SearchActivity;
import com.example.wanjian.creditrent.moudles.signup.view.impl.LoginActivity;
import com.example.wanjian.creditrent.moudles.user.UserFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.activity.LCIMConversationListFragment;
import cn.leancloud.chatkit.utils.LCIMConstants;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager mainViewpager;
    private HomePageFragment homepagerFragment;
    private ChatFragment chatFragment;
    private LCIMConversationListFragment lcimConversationListFragment;
    private UserFragment userFragment;
    private MainViewpagerAdapter mainViewpagerAdapter;
    private ImageView toolbarSearchview,ivOne,ivTwo,ivThree;
    private TextView tvOne,tvTwo,tvThree,tv_title;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private TextView toolbarTextview;

    List<Fragment> fragmentList;

    //上次按下返回键的系统时间
    private long lastBackTime = 0;
    //当前按下返回键的系统时间
    private long currentBackTime = 0;

    private Boolean isLogin = SharedPreferencesUtil.getIsLogin();

    private Boolean isHaveOrder = false;    //是否有订单消息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initToolbar();

        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);

    }

    @Override
    protected void onResume() {
        isLogin = SharedPreferencesUtil.getIsLogin();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        isLogin = SharedPreferencesUtil.getIsLogin();
        super.onRestart();
    }

    private void initView() {
        mainViewpager = (ViewPager) findViewById(R.id.activity_main_viewPager);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.activity_main_floatingactionbutton);
        floatingActionButton.setOnClickListener(this);
        fragmentList = new ArrayList<Fragment>();
        userFragment = new UserFragment();
        chatFragment =new ChatFragment();
        lcimConversationListFragment = new LCIMConversationListFragment();
        homepagerFragment = new HomePageFragment();
        fragmentList.add(homepagerFragment);

//        fragmentList.add(chatFragment);

        if (isLogin){
            fragmentList.add(lcimConversationListFragment);
        }else {
            fragmentList.add(chatFragment);
        }

        fragmentList.add(userFragment);
        mainViewpagerAdapter = new MainViewpagerAdapter(getSupportFragmentManager(),fragmentList);
        mainViewpager.setOffscreenPageLimit(3);  //设置预加载
        mainViewpager.setAdapter(mainViewpagerAdapter);
        toolbarSearchview=(ImageView)findViewById(R.id.toolbar_searchview);
        toolbarTextview = (TextView) findViewById(R.id.toolbar_textview);
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
        toolbarTextview.setOnClickListener(this);
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
            toolbarSearchview.setBackgroundResource(R.drawable.ic_search_white_24dp);
            toolbarTextview.setVisibility(View.GONE);
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
                toolbarSearchview.setBackgroundResource(R.drawable.ic_search_white_24dp);
                toolbarTextview.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                break;
            case R.id.linear2_iv:
            case R.id.linear2_tv:
                mainViewpager.setCurrentItem(1);
                tv_title.setText("对话");
                toolbarSearchview.setVisibility(View.VISIBLE);
                toolbarSearchview.setBackgroundResource(R.drawable.nomessage);
                getIfHavaOrder();
                toolbarTextview.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.VISIBLE);
                break;
            case R.id.linear3_iv:
            case R.id.linear3_tv:
                mainViewpager.setCurrentItem(2);
                tv_title.setText("个人中心");
                toolbarSearchview.setVisibility(View.GONE);
                toolbarTextview.setVisibility(View.VISIBLE);
                toolbarTextview.setText("联系客服");
                floatingActionButton.setVisibility(View.GONE);
                break;
            case R.id.toolbar_searchview:
                if (mainViewpager.getCurrentItem() == 0){
                    startIntentActivity(this, new SearchActivity());
                }else if (mainViewpager.getCurrentItem() == 1){
                    startIntentActivity(this,new OrdersActivity());
                    ToastUtil.show("对话");
                }
                break;
            case R.id.toolbar_textview:
                if(isLogin){
                    LCChatKitUser lcChatKitUser =new LCChatKitUser(ACache.getDefault().getAsString(C.USER_NAME),
                            ACache.getDefault().getAsString(C.NICKNAME),"http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg");
                    LCChatKit.getInstance().open(lcChatKitUser.getUserId(), new AVIMClientCallback() {
                        @Override
                        public void done(AVIMClient avimClient, AVIMException e) {
                            if (null == e) {
                                Intent intent = new Intent(MainActivity.this, LCIMConversationActivity.class);
                                intent.putExtra(LCIMConstants.PEER_ID, "15879283850");
                                startActivity(intent);
                            } else {
                                ToastUtil.show(e.toString());
                            }
                        }
                    });

                }else {
                    startIntentActivity(this,new LoginActivity());
                    ToastUtil.show("请先登录后再操作~");
                }
                break;
            case R.id.activity_main_floatingactionbutton:
                if (isLogin){
                    startIntentActivity(this,new RentCarActivity());
                }else {
                    startIntentActivity(this,new LoginActivity());
                    ToastUtil.show("您还未登录，请登录后再查看~");
                }
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
                toolbarSearchview.setBackgroundResource(R.drawable.ic_search_white_24dp);
                toolbarTextview.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                tv_title.setText("首页");
                ivOne.setImageResource(R.mipmap.home_seclect);
                ivTwo.setImageResource(R.mipmap.service_unseclect);
                ivThree.setImageResource(R.mipmap.my_unseclect);
                tvOne.setTextColor(this.getResources().getColor(R.color.main_toolbar));
                tvTwo.setTextColor(this.getResources().getColor(R.color.textColor));
                tvThree.setTextColor(this.getResources().getColor(R.color.textColor));
                break;
            case 1:
                toolbarSearchview.setVisibility(View.VISIBLE);

                toolbarSearchview.setBackgroundResource(R.drawable.nomessage);
                getIfHavaOrder();

                toolbarTextview.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.VISIBLE);
                tv_title.setText("对话");
                ivOne.setImageResource(R.mipmap.home_unseclect);
                ivTwo.setImageResource(R.mipmap.service_seclect);
                ivThree.setImageResource(R.mipmap.my_unseclect);
                tvOne.setTextColor(this.getResources().getColor(R.color.textColor));
                tvTwo.setTextColor(this.getResources().getColor(R.color.main_toolbar));
                tvThree.setTextColor(this.getResources().getColor(R.color.textColor));
                break;
            case 2:
                toolbarSearchview.setVisibility(View.GONE);
                toolbarTextview.setVisibility(View.VISIBLE);
                toolbarTextview.setText("联系客服");
                floatingActionButton.setVisibility(View.GONE);
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

    private void getIfHavaOrder() {
        if (isLogin){
            RetrofitNewSingleton.getInstance()
                    .getOrderToMe(null)
                    .subscribe(new Observer<ArrayList<OrderBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ArrayList<OrderBean> value) {
                            if (!value.isEmpty()){
                                isHaveOrder = true;
                                toolbarSearchview.setBackgroundResource(R.drawable.message);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            isHaveOrder = false;
                        }

                        @Override
                        public void onComplete() {
                            isHaveOrder = true;
                        }
                    });
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    //按两次退出app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //捕获返回键按下的事件
        if(keyCode == KeyEvent.KEYCODE_BACK){
            currentBackTime = System.currentTimeMillis();
            if(currentBackTime - lastBackTime > 2 * 1000){
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                lastBackTime = currentBackTime;
            }else{ //如果两次按下的时间差小于2秒，则退出程序
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
