package com.example.wanjian.creditrent.moudles.homepage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseFragment;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.homepage.kinds.Books;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.HomepagerAdapter;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.HomepagerGoodsList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wanjian on 2017/10/25.
 */

public class HomePageFragment extends BaseFragment implements OnItemClickListener {


    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private List<String> networkImages;
    private String[] images = {
            "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://img07.tooopen.com/images/20170412/tooopen_sy_205630266491.jpg",
            "http://img07.tooopen.com/images/20170811/tooopen_sy_219898696246.jpg",
            "http://img06.tooopen.com/images/20170321/tooopen_sy_202673188311.jpg"
    };
    private ImageLoader imageLoader;
    private HomepagerAdapter homepagerAdapter;

    private List<HomepagerGoodsList> homepagerGoodsList=new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepager, null);

        initViews(view);
        initData();

        //convenientBanner 的用法
        networkImages= Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages)
                .setPointViewVisible(true)
                .startTurning(2000)
                .setOnItemClickListener(this)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});



        recyclerView=(RecyclerView)view.findViewById(R.id.homepager_recyclerview);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        homepagerAdapter=new HomepagerAdapter(homepagerGoodsList);
        recyclerView.setAdapter(homepagerAdapter);


        refreshData(view);

        loadmoreData(view);


        return view;
    }



    private void initData() {
        for (int i=1;i<10;i++){
            HomepagerGoodsList homepagerGoods=new HomepagerGoodsList(R.mipmap.ic_launcher,"数据结构"+i,"10元","九成新");
            homepagerGoodsList.add(homepagerGoods);
        }
    }


    private void initViews(View view) {
        convenientBanner=(ConvenientBanner)view.findViewById(R.id.convenientbanner);
        //初始化ImageLoader图片加载库
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));


        //点击事件
        LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.homepager_kinds_books);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntentActivity(getContext(),new Books());
            }
        });


    }


    //下拉增加数据
    private void refreshData(View view) {
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.homepager_SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        List<HomepagerGoodsList> homepagerGoods=new ArrayList<HomepagerGoodsList>();
                        for (int i = 0; i <5; i++) {
                            int index = i + 1;
                            HomepagerGoodsList list=new HomepagerGoodsList(R.mipmap.ic_launcher,"new 数据结构"+index,"10元"+index,"九成新"+index);
                            homepagerGoods.add(list);
                        }
                        homepagerAdapter.addItem(homepagerGoods);
                        swipeRefreshLayout.setRefreshing(false);
                        ToastUtil.show("更新了五条数据...");
                    }
                });
            }
        });

    }


    //上拉加载更多
    private void loadmoreData(View view) {

        recyclerView=(RecyclerView)view.findViewById(R.id.homepager_recyclerview);
//        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int currentPage) {
//                List<HomepagerGoodsList> moreList = new ArrayList<>();
//                for (int i = 10; i < 13; i++) {
//                    HomepagerGoodsList homepagerGoodsList=new HomepagerGoodsList(R.mipmap.ic_launcher,"更多的数据 数据结构","10元","九成新");
//                    moreList.add(homepagerGoodsList);
//                    homepagerAdapter.notifyDataSetChanged();
//                    ToastUtil.show("加载完毕");
//                }
//            }
//        });

        int lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem-1==homepagerAdapter.getItemCount()){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<HomepagerGoodsList> newDatas=new ArrayList<HomepagerGoodsList>();
                            HomepagerGoodsList homepagerGoodsList=new HomepagerGoodsList(R.mipmap.ic_launcher,"更多的数据 数据结构","10元","九成新");
                            for (int i=0;i<5;i++){
                                newDatas.add(homepagerGoodsList);
                            }
                            homepagerAdapter.addMoreItem(newDatas);
                        }
                    },500);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
            }
        });


    }



    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(),"你点击了第"+position+"张图片",Toast.LENGTH_SHORT).show();
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



}
