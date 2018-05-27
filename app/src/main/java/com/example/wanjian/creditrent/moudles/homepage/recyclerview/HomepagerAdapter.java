package com.example.wanjian.creditrent.moudles.homepage.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.common.util.Utils;

import java.util.List;


/**
 * Created by wanjian on 2017/11/30.
 * 参考博客：https://my.oschina.net/ryaneLee/blog/879137
 */

public class HomepagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    private List<HomepagerGoodsList> mHomepagerGoodsLists;
    private Context context;
    private int normalType = 0;
    private int footType = 1;

    private boolean hasMore = true;   // 变量，是否有更多数据


    public HomepagerAdapter(List<HomepagerGoodsList> list,Context context,boolean hasMore){
        mHomepagerGoodsLists = list;
        this.context = context;
        this.hasMore = hasMore;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 根据返回的ViewType，绑定不同的布局文件
        if (viewType == normalType) {
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_homepager_item,parent,false);
            NormalHolder holder=new NormalHolder(view);
            return holder;
        } else {
            if (Utils.isNetworkConnected(context)){
                View view= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_homepager_item_foot,parent,false);
                FootHolder holder=new FootHolder(view);
                return holder;
            }else {
                View view= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_homepager_noconnet,parent,false);
                FootHolder holder=new FootHolder(view);
                return holder;
            }
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NormalHolder){
            HomepagerGoodsList homepagerGoodsList=mHomepagerGoodsLists.get(position);

            Glide.with(context)
                    .load(homepagerGoodsList.getGoodsimg1())
                    .into(((NormalHolder) holder).avatar);
            ((NormalHolder) holder).name.setText(homepagerGoodsList.getGoodsname());
            ((NormalHolder) holder).price.setText(homepagerGoodsList.getChuzumoney());
            ((NormalHolder) holder).description.setText(homepagerGoodsList.getDescription());

            String id = homepagerGoodsList.getGoodsid();

            ((NormalHolder) holder).detailinformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GoodsDetailinformationActivity.class);
                    intent.putExtra("GoodId",id);
                    context.startActivities(new Intent[]{intent});
                }
            });

        }else {
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                if (mHomepagerGoodsLists.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                //判断网络
                if (Utils.isNetworkConnected(context)){
                    if (mHomepagerGoodsLists.size() == 0) {
                        // 如果查询数据发现并没有增加时，就显示没有更多数据了
                        ((FootHolder) holder).tips.setText("没有更多数据了");
                    }
                }else {
                    if (mHomepagerGoodsLists.size() == 0) {
                        ((FootHolder) holder).tips.setText("网络不给力");
                    }
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mHomepagerGoodsLists.size()+1;
    }

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
    public int getRealLastPosition() {
        return mHomepagerGoodsLists.size();
    }

    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }



    // 正常item的ViewHolder，用以缓存findView操作
    class NormalHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name,price,description;
        Button detailinformation;

        public NormalHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.homepager_item_avatar);
            name = itemView.findViewById(R.id.homepager_item_name);
            price = itemView.findViewById(R.id.homepager_item_price);
            description = itemView.findViewById(R.id.homepager_item_description);
            detailinformation = itemView.findViewById(R.id.homepager_item_detailinfornation);
        }
    }

    // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = itemView.findViewById(R.id.item_tip);
        }
    }



    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空
    public void resetDatas() {
        mHomepagerGoodsLists.clear();
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<HomepagerGoodsList> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            mHomepagerGoodsLists.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }


}
