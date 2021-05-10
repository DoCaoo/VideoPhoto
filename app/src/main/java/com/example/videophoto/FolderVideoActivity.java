package com.example.videophoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.videophoto.adapter.AdapterVideoFolder;
import com.example.videophoto.adapter.AdapterVideoList;
import com.example.videophoto.model.ModelFolder;
import com.example.videophoto.model.ModelVideo;

import java.util.ArrayList;

public class FolderVideoActivity extends AppCompatActivity {
    AdapterVideoFolder adapter;
    protected RecyclerView recyclerView;
    ArrayList<ModelFolder> arrayList = new ArrayList<>();
    ModelVideo modelVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_video);

        setTitle("Folder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rc_view);

//         loadVideo();
        selectFolder();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new AdapterVideoFolder(arrayList, this);
        recyclerView.setAdapter(adapter);


    }
    protected void selectFolder() {
        try {
            ArrayList<String> videoPaths = new ArrayList<>();
            Uri allVideosuri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.BUCKET_DISPLAY_NAME};
            Cursor cursor = getContentResolver().query(allVideosuri, projection, null, null, null);
            cursor.moveToFirst();
            do {
                ModelFolder fold = new ModelFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                String folderpaths = datapath.replace(name, "");
                if (!videoPaths.contains(folderpaths)) {
                    videoPaths.add(folderpaths);
                    fold.setPath(folderpaths);
                    fold.setName(folder);
                    arrayList.add(fold);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("EEEEEE", arrayList.size()+"");
    }


}