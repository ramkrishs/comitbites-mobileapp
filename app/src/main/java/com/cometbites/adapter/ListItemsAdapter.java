package com.cometbites.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cometbites.R;
import com.cometbites.model.FoodJoint;
import com.cometbites.model.Item;

import java.util.List;

/**
 * Created by Ramakrishnan_sathyav on 11/17/2016.
 */

public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.MyViewHolder> {

    List<Item> mData;

    private LayoutInflater inflater;
    public ListItemsAdapter(Context context, List<Item> data){
        inflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Item current = mData.get(position);
        holder.setData(current,position);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView itemImage;
        TextView itemPrice;
        int position;
        Item current;

        public MyViewHolder(View itemView) {
            super(itemView);
            title       = (TextView)  itemView.findViewById(R.id.itemText);
            itemPrice   = (TextView)  itemView.findViewById(R.id.itemPrice);
            itemImage   = (ImageView) itemView.findViewById(R.id.itemImage);

        }

        public void setData(Item current, int position) {
            this.title.setText(current.getName());
            this.itemImage.setImageResource(current.getImageID());
            this.itemPrice.setText(current.getPrice().toString());
            this.position = position;
            this.current = current;
        }
    }
}
