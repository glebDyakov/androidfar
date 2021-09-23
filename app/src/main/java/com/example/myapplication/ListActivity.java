package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    public ArrayList<HashMap<String, Object>> myFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        TextView folderSize = findViewById(R.id.folderSize);
        folderSize.setText(String.valueOf(getApplicationContext().getCacheDir().getTotalSpace()));

        myFiles = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> myFile = new HashMap<String, Object>();
        myFile.put("name", "index.html");
        myFile.put("size", 255);
        myFiles.add(myFile);
        myFile = new HashMap<String, Object>();
        myFile.put("name", "App.js");
        myFile.put("size", 1024);
        myFiles.add(myFile);
        myFile = new HashMap<String, Object>();
        myFile.put("name", "photo.png");
        myFile.put("size", 256);
        myFiles.add(myFile);
        myFile = new HashMap<String, Object>();
        myFile.put("name", "index.js");
        myFile.put("size", 2048);
        myFiles.add(myFile);
        myFile = new HashMap<String, Object>();
        myFile.put("name", "index.php");
        myFile.put("size", 10);
        myFiles.add(myFile);
        LinearLayout layoutOfFiles = findViewById(R.id.layoutOfFiles);

//        if(myFiles.size() >= 1) {
//            Log.d("mytag", "файлов много");
//            for (HashMap<String, Object> localFile : myFiles) {
//                ImageView fileIcon = new ImageView(ListActivity.this);
//                fileIcon.setImageResource(R.drawable.home);
//                TextView fileName = new TextView(ListActivity.this);
//                TextView fileSize = new TextView(ListActivity.this);
//                fileName.setText(localFile.get("name").toString());
//                fileSize.setText(localFile.get("size").toString());
//                LinearLayout layoutOfFile = new LinearLayout(ListActivity.this);
//                layoutOfFile.setOrientation(LinearLayout.HORIZONTAL);
//                layoutOfFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.d("mytag", "Переходим к папке: " + localFile.get("name").toString());
//                    }
//                });
//                layoutOfFile.addView(fileIcon);
//                layoutOfFile.addView(fileName);
//                layoutOfFile.addView(fileSize);
//                layoutOfFiles.addView(layoutOfFile);
//            }
//        } else {
//            Log.d("mytag", "файлов нет");
//            LinearLayout layoutOfFile = new LinearLayout(ListActivity.this);
//            TextView filesNotFound = new TextView(ListActivity.this);
//            filesNotFound.setText("В этой папке ещё нет ни одного файла");
//            layoutOfFile.addView(filesNotFound);
//        }

        Log.d("mytag", getApplicationContext().getCacheDir().getPath());
        if(myFiles.size() >= 1) {
            Log.d("mytag", "файлов много");
            for(File fileInDir : getApplicationContext().getCacheDir().listFiles()){
                ImageView fileIcon = new ImageView(ListActivity.this);
                fileIcon.setImageResource(R.drawable.home);
                TextView fileName = new TextView(ListActivity.this);
                TextView fileSize = new TextView(ListActivity.this);
                fileName.setText(fileInDir.getName());
                fileSize.setText(String.valueOf(fileInDir.getTotalSpace()));
                LinearLayout layoutOfFile = new LinearLayout(ListActivity.this);
                layoutOfFile.setOrientation(LinearLayout.HORIZONTAL);
                layoutOfFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("mytag", "Переходим к папке: " + fileInDir.getName());
                    }
                });
                layoutOfFile.addView(fileIcon);
                layoutOfFile.addView(fileName);
                layoutOfFile.addView(fileSize);
                layoutOfFiles.addView(layoutOfFile);
            }
        } else {
            Log.d("mytag", "файлов нет");
            LinearLayout layoutOfFile = new LinearLayout(ListActivity.this);
            TextView filesNotFound = new TextView(ListActivity.this);
            filesNotFound.setText("В этой папке ещё нет ни одного файла");
            layoutOfFile.addView(filesNotFound);
        }

    }

}
