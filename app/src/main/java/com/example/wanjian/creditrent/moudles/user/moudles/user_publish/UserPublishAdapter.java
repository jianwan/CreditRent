package com.example.wanjian.creditrent.moudles.user.moudles.user_publish;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.common.util.ToastUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanjian on 2017/11/15.
 */

public class UserPublishAdapter extends RecyclerView.Adapter<UserPublishAdapter.ViewHolder> {

    private List<UserPublishList> mUserPublished;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView goodsImage;
        TextView goodsName,puhlishedTime, peopleNumber;
        CardView cardview;
        Button checkapplicantlist,unpublished;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsImage=(CircleImageView)itemView.findViewById(R.id.user_published_item_circleimageview);
            goodsName=(TextView) itemView.findViewById(R.id.user_published_item_name);
            puhlishedTime=(TextView)itemView.findViewById(R.id.user_published_item_publishtime);
            peopleNumber=(TextView)itemView.findViewById(R.id.user_published_item_peoplenumber);

            checkapplicantlist=(Button)itemView.findViewById(R.id.user_published_item_checkapplicantlist);
            unpublished=(Button)itemView.findViewById(R.id.user_published_item_unpublished);

            cardview=(CardView)itemView.findViewById(R.id.user_published_item_cardview);

        }
    }


    public UserPublishAdapter(List<UserPublishList> userPublishLists){
        mUserPublished=userPublishLists;
    }


    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_published_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserPublishList userPublishList=mUserPublished.get(position);
        holder.goodsImage.setImageResource(userPublishList.getImageId());
        holder.goodsName.setText(userPublishList.getName());
        holder.puhlishedTime.setText(userPublishList.getPublishTime());
        holder.peopleNumber.setText(userPublishList.getNumber());


        //三个点击事件
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("cardview");
            }
        });

        holder.checkapplicantlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("checkapplicantlist");
            }
        });

        holder.unpublished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=position+1;
                ToastUtil.show("你删除了第"+i+"个item"+"名字是："
                        +userPublishList.getName()+"number是："+userPublishList.getNumber());
                removeData(position);

            }
        });
    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mUserPublished.size();
    }


    //移除数据
    public void removeData(int position) {
        mUserPublished.remove(position);
        //刷新默认动画
        notifyItemRemoved(position);
        //刷新删除位置以下的List的Item位置，防止位置错乱
        if (position != mUserPublished.size()) {
            notifyItemRangeChanged(position, mUserPublished.size() - position);
        }
    }

}
