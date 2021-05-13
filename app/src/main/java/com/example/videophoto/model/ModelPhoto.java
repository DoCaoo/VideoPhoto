package com.example.videophoto.model;

import android.graphics.Bitmap;

import java.util.List;

public class ModelPhoto {
    Bitmap bitmap;
    String photoName;
    String photoPath;
    int photoSize;
    boolean isChecked;
    List<Bitmap> bitmaps;

    public ModelPhoto() {
    }

    public ModelPhoto(Bitmap bitmap, String photoName, String photoPath, int photoSize, boolean isChecked) {
        this.bitmap = bitmap;
        this.photoName = photoName;
        this.photoPath = photoPath;
        this.photoSize = photoSize;
        this.isChecked = isChecked;
    }

    public ModelPhoto(Bitmap bitmaps) {
        this.bitmap = bitmaps;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(int photoSize) {
        this.photoSize = photoSize;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
