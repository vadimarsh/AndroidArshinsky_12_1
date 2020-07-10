package com.example.androidarshinsky_12_2;

import android.graphics.drawable.Drawable;

public class DataItem {

    private Drawable image;
    private String title;
    private String subtitle;
    private Drawable imagClick;


    public DataItem(Drawable image, String title, String subtitle,Drawable imagClick) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.imagClick = imagClick;
    }

    public Drawable getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
    public Drawable getImagClick() {
        return imagClick;
    }

}