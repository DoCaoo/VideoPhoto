package com.example.videophoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.example.videophoto.adapter.AdapterImage;
import com.example.videophoto.adapter.AdapterImageBottom;
import com.example.videophoto.adapter.AdapterSlideFolder;
import com.example.videophoto.model.ModelBottomImg;
import com.example.videophoto.model.ModelFolder;
import com.example.videophoto.model.ModelImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlideShowActivity extends AppCompatActivity {
    RecyclerView recyclerViewFolder, recyclerViewImg, recyclerViewImgBottom;

    AdapterSlideFolder adapterFolder;
    AdapterImage adapterImage;
    AdapterImageBottom adapterImageBottom;

    ArrayList<ModelFolder> arrayListFolder = new ArrayList<>();
    ArrayList<ModelImage> arrayListImage = new ArrayList<>();
    ArrayList<ModelBottomImg> arrayListBottom = new ArrayList<>();

    String folder;
    ModelImage item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        setTitle("SlideShow");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewImg = findViewById(R.id.rc_view_img);
        recyclerViewFolder = findViewById(R.id.rc_view_slide);
        recyclerViewImgBottom = findViewById(R.id.rc_view_img_bottom);


        selectFolder();
        layoutFolder();
        layoutImg();
        layoutImgBottom();
        loadImage();
    }


    protected void layoutFolder() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFolder.setLayoutManager(layoutManager);
        recyclerViewFolder.setItemAnimator(new DefaultItemAnimator());
        adapterFolder = new AdapterSlideFolder(arrayListFolder, this);
        recyclerViewFolder.setAdapter(adapterFolder);
    }

    protected void layoutImg() {
        recyclerViewImg.setLayoutManager(new GridLayoutManager(this, 4));
        adapterImage = new AdapterImage(arrayListImage, this);
        recyclerViewImg.setAdapter(adapterImage);
        recyclerViewImg.setItemAnimator(null);
        adapterImage.notifyDataSetChanged();
        adapterImage.setOnItemClickListener(new AdapterImage.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                ModelImage modelImage = arrayListImage.get(pos);
                ModelBottomImg modelBottomImg = new ModelBottomImg(modelImage.getData());
                arrayListBottom.add(modelBottomImg);
                adapterImageBottom = new AdapterImageBottom(arrayListBottom, getApplicationContext());
                recyclerViewImgBottom.setAdapter(adapterImageBottom);
            }
        });

    }

    protected void layoutImgBottom() {
        adapterImageBottom = new AdapterImageBottom(arrayListBottom, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewImgBottom.setLayoutManager(layoutManager);
        recyclerViewImgBottom.setItemAnimator(null);
        recyclerViewImgBottom.setAdapter(adapterImageBottom);
        adapterImage.notifyDataSetChanged();
    }

    protected void selectFolder() {
        arrayListFolder = new ArrayList<>();
        ArrayList<String> folderPaths = new ArrayList<>();
        Uri allVideosuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(allVideosuri, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                ModelFolder fold = new ModelFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                String folderpaths = datapath.replace(name, "");
                if (!folderPaths.contains(folderpaths)) {
                    folderPaths.add(folderpaths);
                    fold.setPath(folderpaths);
                    fold.setName(folder);
                    arrayListFolder.add(fold);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImage() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME};
                String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

                Cursor cursor = getApplication().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
                if (cursor != null) {
                    int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                    int titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

                    while (cursor.moveToNext()) {
                        long id = cursor.getLong(idColumn);
                        String title = cursor.getString(titleColumn);

                        Uri data = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                        arrayListImage.add(new ModelImage(id, data, title));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapterImage.notifyItemInserted(arrayListImage.size() - 1);
                            }
                        });
                    }
                }
            }
        }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }
}