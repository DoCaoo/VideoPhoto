package com.example.videophoto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SettingActivity extends AppCompatActivity {
    TextView tvFileFormat, tvQuality, tvSize;

    RadioGroup radioGroup;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor; //đưa dữ liệu vào và có thể cỉnh sửa trong sharedPreferences

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvFileFormat = findViewById(R.id.tv_file_format);
        tvQuality = findViewById(R.id.tv_quality);
        tvSize = findViewById(R.id.tv_size);

        radioGroup = findViewById(R.id.radioGroup);


        sharedPreferences = getSharedPreferences("Setting",Context.MODE_PRIVATE);
        //ghi dữ liệu vào sharedPreferences
        editor = sharedPreferences.edit();
        //đọc dữ liệu từ sharedPreferences
        String file_format = sharedPreferences.getString("file_format", "JPG");
        String quality = sharedPreferences.getString("quality", "Best");
        String size = sharedPreferences.getString("size", "0.5x");
        tvFileFormat.setText(file_format);
        tvQuality.setText(quality);
        tvSize.setText(size);
    }

    public void clickFileFormat(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        View v = LayoutInflater.from(SettingActivity.this).inflate(R.layout.dialog_fileformat, null);
        builder.setTitle("File Format");
        RadioButton radioJPG = (RadioButton) v.findViewById(R.id.radio_jpg);
        RadioButton radioPNG = (RadioButton) v.findViewById(R.id.radio_png);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (radioJPG.isChecked()) {
                    tvFileFormat.setText(radioJPG.getText().toString());
                    editor.putString("file_format", "JPG");
                } else {
                    tvFileFormat.setText(radioPNG.getText().toString());
                    editor.putString("file_format", "PNG");
                }
                editor.apply();
                editor.commit();
            }

        });
       builder.setView(v);
       builder.show();
    }

    public void clickQuality(View view) {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(SettingActivity.this);
        View v = LayoutInflater.from(SettingActivity.this).inflate(R.layout.dialog_quality, null);
        builder2.setTitle("Quality");
        RadioButton radioBest = (RadioButton) v.findViewById(R.id.radio_best);
        RadioButton radioVeyHigh = (RadioButton) v.findViewById(R.id.radio_very_high);
        RadioButton radioHigh = (RadioButton) v.findViewById(R.id.radio_high);
        RadioButton radioMedium = (RadioButton) v.findViewById(R.id.radio_medium);
        RadioButton radioLow = (RadioButton) v.findViewById(R.id.radio_low);
        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (radioBest.isChecked()) {
                    tvQuality.setText(radioBest.getText().toString());
                    editor.putString("quality", "Best");
                } else if (radioVeyHigh.isChecked()){
                    tvQuality.setText(radioVeyHigh.getText().toString());
                    editor.putString("quality", "Very High");
                }else if (radioHigh.isChecked()) {
                    tvQuality.setText(radioHigh.getText().toString());
                    editor.putString("quality", "High");
                }else if (radioMedium.isChecked()) {
                    tvQuality.setText(radioMedium.getText().toString());
                    editor.putString("quality", "Medium");
                }else {
                    tvQuality.setText(radioLow.getText().toString());
                    editor.putString("quality", "Low");
                }
                editor.apply();
                editor.commit();
            }

        });
        builder2.setView(v);
        builder2.show();
    }

    public void clickSize(View view) {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(SettingActivity.this);
        View v = LayoutInflater.from(SettingActivity.this).inflate(R.layout.dialog_size, null);
        builder3.setTitle("Size");
        RadioButton rd05 = (RadioButton) v.findViewById(R.id.rd05);
        RadioButton rd1 = (RadioButton) v.findViewById(R.id.rd1);
        RadioButton rd15 = (RadioButton) v.findViewById(R.id.rd15);
        RadioButton rd2 = (RadioButton) v.findViewById(R.id.rd2);
        RadioButton rd3 = (RadioButton) v.findViewById(R.id.rd3);
        builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (rd05.isChecked()) {
                    tvSize.setText(rd05.getText().toString());
                    editor.putString("size", "0.5x");
                } else if (rd1.isChecked()){
                    tvSize.setText(rd1.getText().toString());
                    editor.putString("size", "1x");
                }else if (rd15.isChecked()) {
                    tvSize.setText(rd15.getText().toString());
                    editor.putString("size", "1.5x");
                }else if (rd2.isChecked()) {
                    tvSize.setText(rd2.getText().toString());
                    editor.putString("size", "2x");
                }else {
                    tvSize.setText(rd3.getText().toString());
                    editor.putString("size", "3x");
                }
                editor.apply();
                editor.commit();
            }
        });
        builder3.setView(v);
        builder3.show();
    }
}