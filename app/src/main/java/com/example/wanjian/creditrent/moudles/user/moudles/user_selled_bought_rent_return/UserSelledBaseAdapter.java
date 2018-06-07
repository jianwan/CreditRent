package com.example.wanjian.creditrent.moudles.user.moudles.user_selled_bought_rent_return;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.moudles.homepage.kinds.KindsBean;

import java.util.Collection;
import java.util.List;


/**
 * Created by 17631 on 2018/6/1.
 */

public class UserSelledBaseAdapter extends BaseQuickAdapter<KindsBean,BaseViewHolder> {


    private Context mContext;

    public UserSelledBaseAdapter(Context context,List<KindsBean> data) {
        super(data);
        mContext = context;
    }

    public UserSelledBaseAdapter(Context context, int layoutResId, List<KindsBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    public UserSelledBaseAdapter(Context context,@LayoutRes int layoutResId) {
        super(layoutResId);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, KindsBean item) {
        helper.setText(R.id.user_selled_item_goodname,"物品名称："+item.getGoodsname()+item.getGoodsid());
        helper.setText(R.id.user_selled_item_description,"物品描述："+item.getDescription());
        if (item.getErshousell().equals("0")){
            helper.setText(R.id.user_selled_item_sellorrent,"出租or出售:  出租");

        }else {
            helper.setText(R.id.user_selled_item_sellorrent,"出租or出售:  出售");
        }

        Glide.with(mContext).load(item.getGoodsimg1()).crossFade().into((ImageView) helper.getView(R.id.user_selled_item_image));
        helper.setText(R.id.user_selled_item_price,"价格:  "+item.getChuzumoney());

    }

    @Override
    public void replaceData(@NonNull Collection<? extends KindsBean> data) {
        super.replaceData(data);
    }
}
