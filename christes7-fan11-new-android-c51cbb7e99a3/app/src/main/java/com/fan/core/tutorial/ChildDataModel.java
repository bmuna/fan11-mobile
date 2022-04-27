package com.fan.core.tutorial;

import android.util.Log;

public class ChildDataModel {

    long id;
    int image;
    String title;

    public ChildDataModel(int id, String country, int image) {
        this.setId(id);
        this.setTitle(country);
        this.setImage(image);
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        Log.d("response ","ID: "+getId()+" Title: "+getTitle());
        return super.toString();
    }
}
