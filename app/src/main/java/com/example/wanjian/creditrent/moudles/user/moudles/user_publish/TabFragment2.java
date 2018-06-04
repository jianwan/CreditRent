package com.example.wanjian.creditrent.moudles.user.moudles.user_publish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseFragment;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.homepage.kinds.KindsBean;
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.GoodsDetailinformationActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 17631 on 2018/6/3.
 */

public class TabFragment2 extends BaseFragment {

    private List<KindsBean> kindsBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    private EasyRefreshLayout easyRefreshLayout;
    private UserPublishBaseAdapter userPublishBaseAdapter;

    int page = 1;
    int TYPE_GOOD_UNDERPUBLISHED = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_publish_tablelayout,null);

        getPublishGoods(view,page,TYPE_GOOD_UNDERPUBLISHED);


        recyclerView = view.findViewById(R.id.user_publish_tablayout_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        easyRefreshLayout = view.findViewById(R.id.user_publish_tablayout_easyrefreshlayout);
        easyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                getPublishGoods(view,page,TYPE_GOOD_UNDERPUBLISHED);
                easyRefreshLayout.loadMoreComplete();
                ToastUtil.show("加载完毕");
            }

            @Override
            public void onRefreshing() {
                kindsBeen.clear();
                page = 1;
                getPublishGoods(view,1,TYPE_GOOD_UNDERPUBLISHED);
                easyRefreshLayout.refreshComplete();
                ToastUtil.show("刷新完毕");
            }
        });
        return view;
    }


    private void getPublishGoods(View view,int intpage,int type) {
        RetrofitNewSingleton.getInstance()
                .getUserGoodsByType(intpage,type)
                .subscribe(new Observer<ArrayList<KindsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<KindsBean> value) {
                        for (int i=0;i<value.size();i++){
                            kindsBeen.add(value.get(i));
                        }
                        userPublishBaseAdapter = new UserPublishBaseAdapter(getContext(),R.layout.fragment_user_publish_tablelayout_recyclerview_item,kindsBeen,1);
                        recyclerView.setAdapter(userPublishBaseAdapter);


                        page ++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show("暂无更多数据");
                    }

                    @Override
                    public void onComplete() {

                        userPublishBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                startIntentActivity(getActivity(),new GoodsDetailinformationActivity(),"GoodId",kindsBeen.get(position).getGoodsid());
                            }
                        });
                    }
                });
    }


}
