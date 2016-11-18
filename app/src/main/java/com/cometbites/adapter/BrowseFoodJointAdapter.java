package com.cometbites.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cometbites.R;
import com.cometbites.app.ListItemsActivity;
import com.cometbites.model.FoodJoint;

import java.util.List;

/**
 * Created by Ramakrishnan_sathyav on 11/17/2016.
 */

public class BrowseFoodJointAdapter  extends RecyclerView.Adapter<BrowseFoodJointAdapter.MyViewHolder> {

    List<FoodJoint> mData;

    private LayoutInflater inflater;
    public  BrowseFoodJointAdapter(Context context, List<FoodJoint> data){
        inflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.foodjoint_list_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        FoodJoint current = mData.get(position);
        holder.setData(current,position);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,waitTime;
        ImageView foodJointImage;
        int position;
        FoodJoint current;

        public MyViewHolder(final View itemView) {
            super(itemView);
            title       = (TextView)  itemView.findViewById(R.id.foodJointTitle);
            waitTime       = (TextView)  itemView.findViewById(R.id.foodJointWaitTime);
            foodJointImage    = (ImageView) itemView.findViewById(R.id.foodJointImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemView.getContext().startActivity(new Intent(itemView.getContext(),ListItemsActivity.class));
                }
            });
        }

        public void setData(FoodJoint current, int position) {
            this.title.setText(current.getName());
            this.foodJointImage.setImageResource(current.getLogoID());
            this.waitTime.setText(current.getWait_time());
            this.position = position;
            this.current = current;
        }
    }
}
