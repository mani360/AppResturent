package com.andygopu.androidantariksa.appresturent;

import android.widget.ImageView;
import android.widget.TextView;

public class Food {
    private String name,desc,price,image;


    public Food(String name, String desc, String price, String image) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }


    public String getImage() {
        return image;
    }



}
