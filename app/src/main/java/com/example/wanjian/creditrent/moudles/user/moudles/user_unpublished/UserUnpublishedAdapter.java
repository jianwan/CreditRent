package com.example.wanjian.creditrent.moudles.user.moudles.user_unpublished;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.common.util.ToastUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by wanjian on 2017/11/17.
 */

public class UserUnpublishedAdapter extends RecyclerView.Adapter<UserUnpublishedAdapter.ViewHolder> {

    private List<UserUnpublished> mUserUnpublished;

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardview;
        CircleImageView circleImageView;
        TextView name,overgrade,price;
        Button check,published,delect;

        public ViewHolder(View itemView) {
            super(itemView);
            cardview=itemView.findViewById(R.id.user_unpublished_item_cardview);
            circleImageView=itemView.findViewById(R.id.user_unpublished_item_circleimageview);
            name=itemView.findViewById(R.id.user_unpublished_item_name);
            overgrade=itemView.findViewById(R.id.user_unpublished_item_overgrade);
            price=itemView.findViewById(R.id.user_unpublished_item_price);
            check=itemView.findViewById(R.id.user_unpublished_item_check);
            published=itemView.findViewById(R.id.user_unpublished_item_published);
            delect=itemView.findViewById(R.id.user_unpublished_item_delect);
        }
    }

    public UserUnpublishedAdapter(List<UserUnpublished> userUnpublisheds){
        mUserUnpublished=userUnpublisheds;
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_unpublished_item,parent,false);
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
        UserUnpublished userUnpublished=mUserUnpublished.get(position);
        holder.circleImageView.setImageResource(userUnpublished.getImageId());
        holder.name.setText(userUnpublished.getName());
        holder.overgrade.setText(userUnpublished.getOvergrade());
        holder.price.setText(userUnpublished.getPrice());

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("check");
            }
        });

        holder.published.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("publish");
            }
        });

        holder.delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("delect");
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
        return mUserUnpublished.size();
    }


}
