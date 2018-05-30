package com.example.wanjian.creditrent.moudles.user.moudles.user_collection;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanjian.creditrent.R;

import java.util.List;

/**
 * Created by 17631 on 2018/5/21.
 */

public class UserCollectionAdapter extends BaseItemDraggableAdapter<UserCollectionBean,BaseViewHolder> {


    public UserCollectionAdapter(List<UserCollectionBean> data) {
        super(data);
    }

    public UserCollectionAdapter(int layoutResId, List<UserCollectionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCollectionBean item) {
        helper.setText(R.id.collect_item_goodname,item.getGoodsname());
        helper.setText(R.id.collect_item_status,item.getGoodsststus());
    }
}
