package com.andygopu.androidantariksa.appresturent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class FirebaseRestaurantViewHolder extends
        RecyclerView.ViewHolder implements View.OnClickListener{

    View mView;
    Context mContext;
    public FirebaseRestaurantViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
