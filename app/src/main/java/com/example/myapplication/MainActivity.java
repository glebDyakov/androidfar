package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button recentFilesWindow = findViewById(R.id.recentFilesWindow);
        recentFilesWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("filesType", "none");
                intent.putExtra("currentPath", getApplicationContext().getCacheDir().getPath());
                MainActivity.this.startActivity(intent);
            }
        });

        ImageButton imageBtn = findViewById(R.id.imageBtn);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("filesType", "images");
                intent.putExtra("currentPath", getApplicationContext().getCacheDir().getPath());
                MainActivity.this.startActivity(intent);
            }
        });

        ImageButton videoBtn = findViewById(R.id.videoBtn);
        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("filesType", "videos");
                intent.putExtra("currentPath", getApplicationContext().getCacheDir().getPath());
                MainActivity.this.startActivity(intent);
            }
        });

        ImageButton audioBtn = findViewById(R.id.audioBtn);
        audioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("filesType", "audios");
                intent.putExtra("currentPath", getApplicationContext().getCacheDir().getPath());
                MainActivity.this.startActivity(intent);
            }
        });

        ImageButton documentBtn = findViewById(R.id.documentBtn);
        documentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("filesType", "documents");
                intent.putExtra("currentPath", getApplicationContext().getCacheDir().getPath());
                MainActivity.this.startActivity(intent);
            }
        });

        ImageButton downloadBtn = findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("filesType", "downloads");
                intent.putExtra("currentPath", getApplicationContext().getCacheDir().getPath());
                MainActivity.this.startActivity(intent);
            }
        });

        ImageButton apkBtn = findViewById(R.id.apkBtn);
        apkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("filesType", "apks");
                intent.putExtra("currentPath", getApplicationContext().getCacheDir().getPath());
                MainActivity.this.startActivity(intent);
            }
        });

        Button analyzeBtn = findViewById(R.id.analyzeBtn);
        analyzeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnalyzeActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}