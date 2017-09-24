package com.snapgroup.Tests;


import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private String title;
    private int DayNumber;

    public ImageItem(Bitmap image, String title,int i) {
        super();
        this.image = image;
        this.title = title;
        DayNumber=i;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDayNumber(int i) {
       this.DayNumber=i;
    }
}