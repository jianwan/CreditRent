package com.example.wanjian.creditrent.moudles.user.moudles.user_orders;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.moudles.chat.order.OrderBean;

import java.util.List;

/**
 * Created by 17631 on 2018/5/26.
 */

public class UserOrdersAdapter extends BaseItemDraggableAdapter<OrderBean,BaseViewHolder> {


    public UserOrdersAdapter(List<OrderBean> data) {
        super(data);
    }

    public UserOrdersAdapter(int layoutResId, List<OrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.setText(R.id.myorder_item_goodname,item.getGoodsname());
        helper.setText(R.id.myorder_item_faqiren,item.getFaqiren());
        helper.setText(R.id.myorder_item_address,item.getAddress());
        helper.setText(R.id.myorder_item_time,item.getTime());
        helper.setText(R.id.myorder_item_status,item.getJiaoyiownerstatus());
        Glide.with(mContext).load(item.getGoodsimg1()).crossFade().into((ImageView) helper.getView(R.id.myorder_item_imageview));

    }


}
