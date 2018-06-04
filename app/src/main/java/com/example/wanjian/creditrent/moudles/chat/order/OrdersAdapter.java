package com.example.wanjian.creditrent.moudles.chat.order;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanjian.creditrent.R;

import java.util.List;

/**
 * Created by 17631 on 2018/5/26.
 */

public class OrdersAdapter extends BaseItemDraggableAdapter<OrderBean,BaseViewHolder> {


    public OrdersAdapter(List<OrderBean> data) {
        super(data);
    }

    public OrdersAdapter(int layoutResId, List<OrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.setText(R.id.order_item_goodname,item.getGoodsname());
        helper.setText(R.id.order_item_faqiren,item.getFaqiren());
        helper.setText(R.id.order_item_address,item.getAddress());
        helper.setText(R.id.order_item_time,item.getTime());
        Glide.with(mContext).load(item.getGoodsimg1()).crossFade().into((ImageView) helper.getView(R.id.order_item_imageview));

        helper.addOnClickListener(R.id.order_item_reject).addOnClickListener(R.id.order_item_accept);

    }


}
