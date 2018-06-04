package com.example.wanjian.creditrent.moudles.homepage.kinds;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanjian.creditrent.R;

import java.util.List;


/**
 * Created by 17631 on 2018/6/1.
 */

public class BasekindsAdapter extends BaseQuickAdapter<KindsBean,BaseViewHolder> {

    private Context mContext;

    public BasekindsAdapter(List<KindsBean> data) {
        super(data);
    }

    public BasekindsAdapter(Context context, int layoutResId, List<KindsBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, KindsBean item) {
        helper.setText(R.id.kinds_item_goodname,"物品名称："+item.getGoodsname());
        helper.setText(R.id.kinds_item_description,"物品描述："+item.getDescription());
        if (item.getErshousell().equals("0")){
            helper.setText(R.id.kinds_item_sellorrent,"出租or出售:  出租");

        }else {
            helper.setText(R.id.kinds_item_sellorrent,"出租or出售:  出售");
        }

        Glide.with(mContext).load(item.getGoodsimg1()).crossFade().into((ImageView) helper.getView(R.id.kinds_item_imageview));
        helper.setText(R.id.kinds_item_price,"价格:  "+item.getChuzumoney());
    }

}
