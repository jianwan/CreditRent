package com.example.wanjian.creditrent.moudles.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseFragment;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.common.util.Utils;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.HomepagerAdapter;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.HomepagerGoodsList;
import com.example.wanjian.creditrent.moudles.signup.view.impl.LoginActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by wanjian on 2017/10/25.
 * TODO: 1、缓存处理，2、recyclerview滑动到最后的闪动
 */

public class HomePageFragment extends BaseFragment implements OnItemClickListener {


    private ConvenientBanner convenientBanner;
    private List<String> networkImages;
    private String[] images = {
            "https://bing.ioliu.cn/v1",
            "https://bing.ioliu.cn/v1/?d=1",
            "https://bing.ioliu.cn/v1/?d=2",
            "https://bing.ioliu.cn/v1/?d=3",
            "https://bing.ioliu.cn/v1/?d=4",
            "https://bing.ioliu.cn/v1/?d=5",
    };
    private ImageLoader imageLoader;
    private HomepagerAdapter homepagerAdapter;

    private ArrayList<HomepagerGoodsList> homepagerGoodsList = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;
    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private Integer page = 2;
    private Boolean isLoadMore = true;     // 是否加载更多数据的标志



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepager, null);

        initViews(view);

        loadRecyclerViewData(view);

        initConvenientBanner();
        initFloatingActionButton(view);

        return view;
    }



    //recyclerview
    private void loadRecyclerViewData(View view) {

        //init recyclerview
        recyclerView = view.findViewById(R.id.homepager_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //下拉刷新
        refreshData(view);


        if (Utils.isNetworkConnected(getContext())){
            loadDataFromWeb(1);
        }else {
            ToastUtil.show("网络不可用,请联网后重试");
            homepagerAdapter=new HomepagerAdapter(homepagerGoodsList,getContext(),false);
            recyclerView.setAdapter(homepagerAdapter);
        }


        //监听nestedScrollView滑动到底部（recyclerview此处监听不到）
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()))  {

                    //TODO：此处因为数据只有三页，后面改变
                    if(page <= 3 ){
                        RetrofitNewSingleton.getInstance()
                                .getHomepagerGoods(page)
                                .subscribe(new Observer<ArrayList<HomepagerGoodsList>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ArrayList<HomepagerGoodsList> value) {
                                        List<HomepagerGoodsList> newList = new ArrayList<HomepagerGoodsList>();
                                        if (value.size()>0){
                                            homepagerAdapter.updateList(value,true);
                                            page++;
                                        }else {
                                            homepagerAdapter.updateList(null,false);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        RetrofitNewSingleton.disposeFailureInfo(e,getContext());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }else {
                        homepagerAdapter=new HomepagerAdapter(homepagerGoodsList,getContext(),false);
                        recyclerView.setAdapter(homepagerAdapter);

                    }

                }
            }
        });

    }

    //下拉刷新数据
    private void refreshData(View view) {
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.homepager_SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        RetrofitNewSingleton.getInstance()
                                .getHomepagerGoods(1)
                                .subscribe(new Observer<ArrayList<HomepagerGoodsList>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ArrayList<HomepagerGoodsList> value) {
                                        homepagerAdapter.resetDatas();
                                        for (int i=0;i<value.size();i++){
                                            homepagerGoodsList.add(value.get(i));
                                        }

                                        homepagerAdapter=new HomepagerAdapter(homepagerGoodsList,getContext(),isLoadMore);
                                        recyclerView.setAdapter(homepagerAdapter);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        RetrofitNewSingleton.disposeFailureInfo(e,getContext());
                                        ToastUtil.show("刷新失败,请检查网络连接");
                                        swipeRefreshLayout.setRefreshing(false);
                                    }

                                    @Override
                                    public void onComplete() {
                                        swipeRefreshLayout.setRefreshing(false);
                                        isLoadMore = false;
                                        page = 2;
                                        ToastUtil.show("数据已更新");
                                    }
                                });
                    }
                });
            }
        });

    }

    //从网络获取数据
    private void loadDataFromWeb(Integer page) {

        RetrofitNewSingleton.getInstance()
                .getHomepagerGoods(page)
                .subscribe(new Observer<ArrayList<HomepagerGoodsList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<HomepagerGoodsList> value) {
                        for (int i=0;i<value.size();i++){
                            homepagerGoodsList.add(value.get(i));
                        }

                        homepagerAdapter=new HomepagerAdapter(homepagerGoodsList,getContext(),isLoadMore);
                        recyclerView.setAdapter(homepagerAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        RetrofitNewSingleton.disposeFailureInfo(e,getContext());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //TODO 待增加本地缓存
    private void loadDataFromLocal() {
        homepagerGoodsList = (ArrayList<HomepagerGoodsList>) ACache.getDefault().getAsObject(C.GOODSINFORMATION);
        homepagerAdapter=new HomepagerAdapter(homepagerGoodsList,getContext(),true);
        recyclerView.setAdapter(homepagerAdapter);
    }




    private void initViews(View view) {
        convenientBanner=(ConvenientBanner)view.findViewById(R.id.convenientbanner);
        //初始化ImageLoader图片加载库
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));


        nestedScrollView = view.findViewById(R.id.homepaer_NestedScrollView);


        //点击事件
        LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.homepager_kinds_books);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferencesUtil.getIsLogin()){
//                    String userId = AVUser.getCurrentUser().getObjectId();

                    //  ACache.getDefault().getAsString(C.USER_NAME) ACache.getDefault().getAsString(C.NICKNAME)

                    LCChatKitUser lcChatKitUser =new LCChatKitUser(ACache.getDefault().getAsString(C.USER_NAME),
                            ACache.getDefault().getAsString(C.NICKNAME),"http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg");
                    LCChatKit.getInstance().open(lcChatKitUser.getUserId(), new AVIMClientCallback() {
                        @Override
                        public void done(AVIMClient avimClient, AVIMException e) {
                            if (null == e) {
                                Intent  intent = new Intent(getActivity(), LCIMConversationActivity.class);
                                intent.putExtra(LCIMConstants.PEER_ID, "Job");
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    startIntentActivity(getContext(), new LoginActivity());
                    ToastUtil.show("请登录后再试");
                }

            }
        });

    }


    //convenientBanner
    private void initConvenientBanner() {
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages)
                .setPointViewVisible(true)
                .startTurning(10000)
                .setOnItemClickListener(this)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }

    //NetworkImageHolderView
    private class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageResource(R.drawable.ic_sync_blue_grey_200_24dp);
            ImageLoader.getInstance().displayImage(data,imageView);
        }
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(),"你点击了第"+position+"张图片",Toast.LENGTH_SHORT).show();
    }


    //FloatingActionButton, 当SDK》25时滑动会有问题，详见 https://blog.csdn.net/libra_louis/article/details/55509839
    private void initFloatingActionButton(View view) {

        FloatingActionsMenu floatingActionsMenu = view.findViewById(R.id.multiple_actions);
        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {

            }

            @Override
            public void onMenuCollapsed() {

            }
        });

        LinearLayout linearLayout = view.findViewById(R.id.homepager_ll);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (floatingActionsMenu.isExpanded()) {
                    floatingActionsMenu.collapse();
                    return true;
                }
                return false;
            }
        });


        //悬浮按钮
        FloatingActionButton floatingActionButtonA = view.findViewById(R.id.action_a);
        floatingActionButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("action_a");
                startIntentActivity(HomePageFragment.this,new UploadGoodActivity());
            }
        });
        FloatingActionButton floatingActionButtonB = view.findViewById(R.id.action_b);
        floatingActionButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("action_b");
            }
        });
    }


}
