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

    private List<KindsBean> kindsBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    private EasyRefreshLayout easyRefreshLayout;
    private UserPublishBaseAdapter userPublishBaseAdapter;

    int page = 1;
    int TYPE_GOOD_UNPUBLISHED = -1;


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
                getPublishGoods(page,TYPE_GOOD_UNPUBLISHED);
                easyRefreshLayout.loadMoreComplete();
                ToastUtil.show("加载完毕");
            }

            @Override
            public void onRefreshing() {
                kindsBeen.clear();
                page = 1;
                getPublishGoods(1,TYPE_GOOD_UNPUBLISHED);
                easyRefreshLayout.refreshComplete();
                ToastUtil.show("刷新完毕");
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
                        for (int i=0;i<value.size();i++){
                            kindsBeen.add(value.get(i));
                        }
                        userPublishBaseAdapter = new UserPublishBaseAdapter(getContext(),R.layout.fragment_user_publish_tablelayout_recyclerview_item,kindsBeen,2);
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
                                                                .userGoodPublised(Integer.parseInt(kindsBeen.get(position).getGoodsid()))
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
                                                                        ToastUtil.show("上架申请提交成功，请耐心等待审核通过~"+kindsBeen.get(position).getGoodsid());
                                                                    }
                                                                });

                                                    }
                                                }).setMessage("确定将该物品重新上架？需要重新审核通过").create();
                                        dialog.show();
                                        break;
                                    case R.id.tablayout_item_changeinformation:
                                        startIntentActivity(getActivity(),new ChangeGoodInformation(),"preGoodId",kindsBeen.get(position).getGoodsid());
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