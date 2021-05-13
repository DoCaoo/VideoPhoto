package com.example.videophoto.model;

import android.net.Uri;

public class ModelImage {
    long id;
    Uri data;
    String title;

    public ModelImage(long id, Uri data, String title) {
        this.id = id;
        this.data = data;
        this.title = title;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Uri getData() {
        return data;
    }

    public void setData(Uri data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
