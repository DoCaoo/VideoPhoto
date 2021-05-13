package com.example.videophoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.videophoto.model.ModelPhoto;
import com.example.videophoto.model.ModelVideo;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wseemann.media.FFmpegMediaMetadataRetriever;


public class PlayerActivity extends AppCompatActivity {
    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;
    boolean playwhenready = false;
    int currentWindow = 0;
    long playbackposition = 0;
    long videoId;
    Uri uri;
    MediaMetadataRetriever mediaMetadataRetriever;
    String path, file_format, quality, size;
    long position;
    int qualityImage;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FileOutputStream fileOutputStream;
    ModelPhoto createdPhotos;
    List<ModelPhoto> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        setTitle("Player Video");

        sharedPreferences();
        playerView = findViewById(R.id.exoplayer_movie);
        videoId = getIntent().getExtras().getLong("videoId");
        path = getIntent().getStringExtra("path");

        initializePlayer();
        mediaMetadataRetriever();


    }


    private void initializePlayer(){
        simpleExoPlayer =  new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);

        Uri videoUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,videoId);
        MediaSource mediaSource = buildMediaSource(videoUri);

        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private void releasePlayer(){
        if (simpleExoPlayer != null){
            playwhenready = simpleExoPlayer.getPlayWhenReady();
            playbackposition =simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        simpleExoPlayer.stop();
        releasePlayer();
        final Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
    private MediaSource buildMediaSource(Uri videoUri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,getString(R.string.app_name));
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
    }

    private void getQuality(String quality) {
        if (quality.equals("Best")) {
            qualityImage = 100;
        } else if (quality.equals("Very High")) {
            qualityImage = 80;
        } else if (quality.equals("High")) {
            qualityImage = 60;
        } else if (quality.equals("Medium")) {
            qualityImage = 40;
        } else {
            qualityImage = 20;
        }
    }
    private void mediaMetadataRetriever() {
        uri = Uri.parse(path);
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this, uri);
    }
    private void sharedPreferences() {
        sharedPreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        file_format = sharedPreferences.getString("file_format", "JPG");
        quality = sharedPreferences.getString("quality", "High");
        size = sharedPreferences.getString("size", "1x");
        getQuality(quality);
    }
    private void getFrames(File file) {
        Bitmap imageBitmap;
        position = playbackposition * 1000;
        imageBitmap = mediaMetadataRetriever.getFrameAtTime(position);
        try {
            if (file_format.equals("JPG")) {
                fileOutputStream = new FileOutputStream(file + File.separator + System.currentTimeMillis() + ".jpg");
                if (fileOutputStream != null) {
                    if (!imageBitmap.compress(Bitmap.CompressFormat.JPEG, qualityImage, fileOutputStream)) {
                        Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                fileOutputStream = new FileOutputStream(file + File.separator + System.currentTimeMillis() + ".png");
                if (fileOutputStream != null) {
                    if (!imageBitmap.compress(Bitmap.CompressFormat.PNG, qualityImage, fileOutputStream)) {
                        Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            createdPhotos = new ModelPhoto(imageBitmap, fileOutputStream + "", file.getAbsolutePath(), Integer.parseInt(file.length() + ""), false);
            arrayList.add(createdPhotos);
            Log.e("AAAAAAAAAAAAAA: ", "" + arrayList.size());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }


//    public void VideoToGif(String uri) {
//        Uri videoFileUri = Uri.parse(uri);
//
//        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
//        retriever.setDataSource(uri);
//        List<Bitmap> rev = new ArrayList<Bitmap>();
//        MediaPlayer mp = MediaPlayer.create(PlayerActivity.this, videoFileUri);
//        int millis = mp.getDuration();
//        System.out.println("starting point");
//        for (int i = 100000; i <=millis * 1000; i += 100000*2) {
//            Bitmap bitmap = retriever.getFrameAtTime(i, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
//            rev.add(bitmap);
//        }
//
//        GiftoImage((ArrayList) rev);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_video,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.may_anh:
                File file = new File("content://media/external/VideoToImage/");
                if (!file.exists()) {
                    file.mkdirs();
                }else {
                    getFrames(file);
                    Toast.makeText(this,"thucngu",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.done:
                Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}