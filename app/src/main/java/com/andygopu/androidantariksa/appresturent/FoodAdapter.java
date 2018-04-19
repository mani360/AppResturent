package com.andygopu.androidantariksa.appresturent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 14-04-2018.
 */

public class FoodAdapter extends ArrayAdapter<Food> {


    public FoodAdapter(@NonNull Context context, int resource, @NonNull List<Food> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((MenuActivity) getContext())
                    .getLayoutInflater()
                    .inflate(R.layout.menu_item, parent, false);
        }
            ImageView image = (ImageView) convertView.findViewById(R.id.imageItem);
            TextView name = (TextView) convertView.findViewById(R.id.tvName);
            TextView desc = (TextView) convertView.findViewById(R.id.tvDesc);
            TextView price = (TextView) convertView.findViewById(R.id.tvPrice);
            Food food=getItem(position);
            Picasso.with(image.getContext()).load(food.getImage()).into(image);
            name.setText(food.getName());
            price.setText(food.getPrice());
            desc.setText(food.getDesc());



            return convertView;
    }
}
