package com.example.videophoto.model;

import android.net.Uri;

public class ModelBottomImg {
    Uri data;

    public ModelBottomImg(Uri data) {
        this.data = data;
    }

    public Uri getData() {
        return data;
    }

    public void setData(Uri data) {
        this.data = data;
    }
}
