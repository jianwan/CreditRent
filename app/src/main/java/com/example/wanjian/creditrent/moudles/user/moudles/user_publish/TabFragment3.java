package com.example.wanjian.creditrent.moudles.user.moudles.user_publish;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.example.wanjian.creditrent.moudles.user.moudles.user_publish.change_good_information.ChangeGoodInformation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 17631 on 2018/6/3.
 */

public class TabFragment3 extends BaseFragment {

    private List<KindsBean> kindBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    private EasyRefreshLayout easyRefreshLayout;
    private UserPublishBaseAdapter userPublishBaseAdapter;

    int page = 1;
    int TYPE_GOOD_UNPUBLISHED = -1;

    Boolean isLoadMore = false;       //是否是加载更多
    Boolean isRefresh = false;        //是否是刷新
    Boolean isFirstLoad = true;        //是否是第一次加载数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_publish_tablelayout,null);

        getPublishGoods(page,TYPE_GOOD_UNPUBLISHED);

        recyclerView = view.findViewById(R.id.user_publish_tablayout_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        easyRefreshLayout = view.findViewById(R.id.user_publish_tablayout_easyrefreshlayout);
        easyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                isLoadMore = true;
                getPublishGoods(page,TYPE_GOOD_UNPUBLISHED);
            }

            @Override
            public void onRefreshing() {
                if (!kindBeen.isEmpty()){
                    userPublishBaseAdapter.getData().clear();
                }
                isRefresh = true;
                page = 1;
                getPublishGoods(1,TYPE_GOOD_UNPUBLISHED);
            }
        });
        return view;
    }


    private void getPublishGoods(int intpage,int type) {
        RetrofitNewSingleton.getInstance()
                .getUserGoodsByType(intpage,type)
                .subscribe(new Observer<ArrayList<KindsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<KindsBean> value) {

                        if (isRefresh){
                            userPublishBaseAdapter.replaceData(value);
                            page ++ ;
                        }


                        if (isFirstLoad){
                            userPublishBaseAdapter = new UserPublishBaseAdapter(getContext(),R.layout.fragment_user_publish_tablelayout_recyclerview_item,value,2);
                            recyclerView.setAdapter(userPublishBaseAdapter);
                            page ++;
                        }


                        if (isLoadMore){
                            userPublishBaseAdapter.getData().addAll(value);
                            userPublishBaseAdapter.notifyDataSetChanged();
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

                        userPublishBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                startIntentActivity(getActivity(),new GoodsDetailinformationActivity(),"GoodId",userPublishBaseAdapter.getItem(position).getGoodsid());
                            }
                        });

                        userPublishBaseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                switch (view.getId()){
                                    case R.id.tablayout_item_umpublished:
                                        //物品下架
                                        AlertDialog dialog = new AlertDialog.Builder(getContext()).setTitle("重新上架")
                                                .setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        RetrofitNewSingleton.getInstance()
                                                                .userGoodPublised(Integer.parseInt(userPublishBaseAdapter.getItem(position).getGoodsid()))
                                                                .subscribe(new Observer<String>() {
                                                                    @Override
                                                                    public void onSubscribe(Disposable d) {

                                                                    }

                                                                    @Override
                                                                    public void onNext(String value) {

                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {
                                                                        RetrofitNewSingleton.disposeFailureInfo(e,getContext());
                                                                    }

                                                                    @Override
                                                                    public void onComplete() {
//                                                                adapter.remove(position);
                                                                        ToastUtil.show("上架申请提交成功，请耐心等待审核通过~"+userPublishBaseAdapter.getItem(position).getGoodsid());
                                                                    }
                                                                });

                                                    }
                                                }).setMessage("确定将该物品重新上架？需要重新审核通过").create();
                                        dialog.show();
                                        break;
                                    case R.id.tablayout_item_changeinformation:
                                        startIntentActivity(getActivity(),new ChangeGoodInformation(),"preGoodId",userPublishBaseAdapter.getItem(position).getGoodsid());
                                        break;
                                    default:
                                        break;
                                }

                            }
                        });
                    }
                });
    }

}
