package com.example.wanjian.creditrent.moudles.chat.rentcar;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanjian.creditrent.R;

import java.util.List;

/**
 * Created by 17631 on 2018/5/26.
 */

public class RentcarAdapter extends BaseItemDraggableAdapter<RentcarBean,BaseViewHolder> {


    public RentcarAdapter(List<RentcarBean> data) {
        super(data);
    }

    public RentcarAdapter(int layoutResId, List<RentcarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RentcarBean item) {
        helper.setText(R.id.rentcar_item_goodname,item.getGoodsname());
        helper.setText(R.id.rentcar_item_goodstatus,item.getGoodsststus());
        Glide.with(mContext).load(item.getGoodsimg()).crossFade().into((ImageView) helper.getView(R.id.rentcar_item_imageview));

        helper.addOnClickListener(R.id.rentcar_item);
    }


}
