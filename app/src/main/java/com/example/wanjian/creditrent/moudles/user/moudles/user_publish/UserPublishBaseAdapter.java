package com.example.wanjian.creditrent.moudles.user.moudles.user_publish;

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

public class UserPublishBaseAdapter extends BaseQuickAdapter<KindsBean,BaseViewHolder> {

    private Context mContext;
    private int id;


    public UserPublishBaseAdapter(Context context, List<KindsBean> data, int tablayoutid) {
        super(data);
        mContext = context;
        id = tablayoutid;
    }

    public UserPublishBaseAdapter(Context context, int layoutResId, List<KindsBean> data,int tablayoutid) {
        super(layoutResId, data);
        mContext = context;
        id = tablayoutid;
    }


    public UserPublishBaseAdapter(Context context,@LayoutRes int layoutResId,int tablayoutid) {
        super(layoutResId);
        mContext = context;
        id = tablayoutid;
    }


    @Override
    protected void convert(BaseViewHolder helper, KindsBean item) {
        helper.setText(R.id.tablayout_item_goodname,"物品名称："+item.getGoodsname()+item.getGoodsid());
        helper.setText(R.id.tablayout_item_description,"物品描述："+item.getDescription());
        if (item.getErshousell().equals("0")){
            helper.setText(R.id.tablayout_item_sellorrent,"出租or出售:  出租");

        }else {
            helper.setText(R.id.tablayout_item_sellorrent,"出租or出售:  出售");
        }

        Glide.with(mContext).load(item.getGoodsimg1()).crossFade().into((ImageView) helper.getView(R.id.tablayout_item_imageview));
        helper.setText(R.id.tablayout_item_price,"价格:  "+item.getChuzumoney());


        if (id == 0){
            helper.addOnClickListener(R.id.tablayout_item_umpublished);
        }else if (id == 1){
            helper.setVisible(R.id.tablayout_item_umpublished,false);
        }else if (id == 2){
            helper.setText(R.id.tablayout_item_umpublished,"重新上架");
            helper.addOnClickListener(R.id.tablayout_item_umpublished);
            helper.setVisible(R.id.tablayout_item_changeinformation,true);
            helper.addOnClickListener(R.id.tablayout_item_changeinformation);
        }


    }

    @Override
    public void replaceData(@NonNull Collection<? extends KindsBean> data) {
        super.replaceData(data);
    }
}
