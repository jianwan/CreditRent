package com.example.wanjian.creditrent.moudles.homepage.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.common.util.ToastUtil;

import java.util.List;


/**
 * Created by wanjian on 2017/11/30.
 */

public class HomepagerAdapter extends RecyclerView.Adapter<HomepagerAdapter.ViewHolder>  {


    private List<HomepagerGoodsList> mHomepagerGoodsLists;



    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView name,price,description;
        Button detailinformation;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar=(ImageView)itemView.findViewById(R.id.homepager_item_avatar);
            name=(TextView)itemView.findViewById(R.id.homepager_item_name);
            price=(TextView)itemView.findViewById(R.id.homepager_item_price);
            description=(TextView)itemView.findViewById(R.id.homepager_item_description);
            detailinformation=(Button)itemView.findViewById(R.id.homepager_item_detailinfornation);
        }
    }

    public HomepagerAdapter(List<HomepagerGoodsList> list){
        mHomepagerGoodsLists=list;
//        mTitles=new ArrayList<String>();
//        for (int i=0;i<20;i++){
//            int index=i+1;
//            mTitles.add("item"+index);
//        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_homepager_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomepagerGoodsList homepagerGoodsList=mHomepagerGoodsLists.get(position);
        holder.avatar.setImageResource(homepagerGoodsList.getAvatar());
        holder.name.setText(homepagerGoodsList.getName());
        holder.price.setText(homepagerGoodsList.getPrice());
        holder.description.setText(homepagerGoodsList.getDescription());

        holder.detailinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("detailinformation");
            }
        });


    }

    @Override
    public int getItemCount() {
        return mHomepagerGoodsLists.size();
    }


    //添加数据
    public void addItem(List<HomepagerGoodsList> newDatas) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        newDatas.addAll(mHomepagerGoodsLists);
        mHomepagerGoodsLists.removeAll(newDatas);
        mHomepagerGoodsLists.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void addMoreItem(List<HomepagerGoodsList> newDatas) {
        mHomepagerGoodsLists.addAll(newDatas);
        notifyDataSetChanged();
    }



}
