package com.example.wanjian.creditrent.moudles.user.moudles.user_collection;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanjian.creditrent.R;

import java.util.List;

/**
 * Created by 17631 on 2018/5/21.
 */

public class UserCollectionAdapter extends BaseQuickAdapter<UserCollectionBean, BaseViewHolder> {


    public UserCollectionAdapter(@LayoutRes int layoutResId, @Nullable List<UserCollectionBean> data) {
        super(layoutResId, data);
    }

    public UserCollectionAdapter(@Nullable List<UserCollectionBean> data) {
        super(data);
    }

    public UserCollectionAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCollectionBean item) {
        helper.setText(R.id.collect_item_goodname,item.getGoodsname());
        helper.setText(R.id.collect_item_status,item.getGoodsststus());
    }
}
