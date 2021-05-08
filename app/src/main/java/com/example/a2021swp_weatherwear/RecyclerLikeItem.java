package com.example.a2021swp_weatherwear;

import android.graphics.drawable.Drawable;

public class RecyclerLikeItem {
    private Drawable iconLikeOuter;
    private Drawable iconLikeTop;
    private Drawable iconLikeBottom;
    private String strLikeOuter, strLikeTop, strLikeBottom;

    public Drawable getIconLikeOuter() {
        return iconLikeOuter;
    }
    public Drawable getIconLikeTop() {
        return iconLikeTop;
    }
    public Drawable getIconLikeBottom() {
        return iconLikeBottom;
    }

    public String getStrLikeOuter() {
        return strLikeOuter;
    }
    public String getStrLikeTop() {
        return strLikeTop;
    }
    public String getStrLikeBottom() {
        return strLikeBottom;
    }


    public void setIconLikeOuter(Drawable iconLikeOuter) {
        this.iconLikeOuter = iconLikeOuter;
    }
    public void setIconLikeTop(Drawable iconLikeTop) {
        this.iconLikeTop = iconLikeTop;
    }
    public void setIconLikeBottom(Drawable iconLikeBottom) {
        this.iconLikeBottom = iconLikeBottom;
    }

    public void setStrLikeOuter(String strLikeOuter) {
        this.strLikeOuter = strLikeOuter;
    }
    public void setStrLikeTop(String strLikeTop) {
        this.strLikeTop = strLikeTop;
    }
    public void setStrLikeBottom(String strLikeBottom) {
        this.strLikeBottom = strLikeBottom;
    }

}
