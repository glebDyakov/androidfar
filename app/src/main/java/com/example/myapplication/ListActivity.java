package com.example.myapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListActivity extends AppCompatActivity {

    public ArrayList<HashMap<String, Object>> myFiles;
    public String measure = "Мб";
    public Long countOfMeasure = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ImageView homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                ListActivity.this.startActivity(intent);
            }
        });

        TextView folderSize = findViewById(R.id.folderSize);

//        if(getApplicationContext().getCacheDir().getTotalSpace() >= 8796093022208l) {
//            countOfMeasure = getApplicationContext().getCacheDir().getTotalSpace() / 8796093022208l;
//            measure = "Гб";
//        } else if(getApplicationContext().getCacheDir().getTotalSpace() >= 8589934592l) {
//            countOfMeasure = getApplicationContext().getCacheDir().getTotalSpace() / 8589934592l;
//            measure = "Мб";
//        } else if(getApplicationContext().getCacheDir().getTotalSpace() >= 8388608l) {
//            countOfMeasure = getApplicationContext().getCacheDir().getTotalSpace() / 8388608l;
//            measure = "Кб";
//        } else if(getApplicationContext().getCacheDir().getTotalSpace() >= 8192l) {
//            countOfMeasure = getApplicationContext().getCacheDir().getTotalSpace() / 8192l;
//            measure = "б";
//        }
        if(getApplicationContext().getCacheDir().length() >= 8796093022208l) {
            countOfMeasure = getApplicationContext().getCacheDir().getTotalSpace() / 8796093022208l;
            measure = "Гб";
        } else if(getApplicationContext().getCacheDir().length() >= 8589934592l) {
            countOfMeasure = getApplicationContext().getCacheDir().getTotalSpace() / 8589934592l;
            measure = "Мб";
        } else if(getApplicationContext().getCacheDir().length() >= 8388608l) {
            countOfMeasure = getApplicationContext().getCacheDir().getTotalSpace() / 8388608l;
            measure = "Кб";
        } else if(getApplicationContext().getCacheDir().length() >= 8192l) {
            countOfMeasure = getApplicationContext().getCacheDir().getTotalSpace() / 8192l;
            measure = "б";
        }

        folderSize.setText(String.valueOf(countOfMeasure) + " " + measure);

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
        Log.d("mytag", getApplicationContext().getFilesDir().getPath());

        File[] listOfFiles = null;
        try {
            listOfFiles = new FileTask(getApplicationContext()).execute("url").get();
            if(listOfFiles.length >= 1) {
                Log.d("mytag", "файлов много");


        //            for(File fileInDir : getApplicationContext().getCacheDir().listFiles()){
                    for(File fileInDir : listOfFiles){
                        Log.d("mytag", "имя файла: " + fileInDir.getName() + "размер файла: " + fileInDir.getTotalSpace());

                        LinearLayout layoutOfFile = new LinearLayout(ListActivity.this);
                        ConstraintLayout.LayoutParams fileLayoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                        fileLayoutParams.setMargins(5,75,5, 75);
                        layoutOfFile.setLayoutParams(fileLayoutParams);

                        CheckBox checkbox = new CheckBox(ListActivity.this);
                        checkbox.setText("");
                        ImageView fileIcon = new ImageView(ListActivity.this);
                        fileIcon.setImageResource(R.drawable.home);
                        fileIcon.setImageTintList(ColorStateList.valueOf(Color.argb(100, 145, 145, 145)));
                        TextView fileName = new TextView(ListActivity.this);
                        TextView fileSize = new TextView(ListActivity.this);
                        fileName.setText(fileInDir.getName());

//                        if(fileInDir.getTotalSpace() >= 8796093022208l) {
//                            countOfMeasure = fileInDir.getTotalSpace() / 8796093022208l;
//                            measure = "Гб";
//                        } else if(fileInDir.getTotalSpace() >= 8589934592l) {
//                            countOfMeasure = fileInDir.getTotalSpace() / 8589934592l;
//                            measure = "Мб";
//                        } else if(fileInDir.getTotalSpace() >= 8388608l) {
//                            countOfMeasure = fileInDir.getTotalSpace() / 8388608l;
//                            measure = "Кб";
//                        } else if(fileInDir.getTotalSpace() >= 8192l) {
//                            countOfMeasure = fileInDir.getTotalSpace() / 8192l;
//                            measure = "б";
//                        }
                        if(fileInDir.length() >= 8796093022208l) {
                            countOfMeasure = fileInDir.length() / 8796093022208l;
                            measure = "Гб";
                        } else if(fileInDir.length() >= 8589934592l) {
                            countOfMeasure = fileInDir.length() / 8589934592l;
                            measure = "Мб";
                        } else if(fileInDir.length() >= 8388608l) {
                            countOfMeasure = fileInDir.length() / 8388608l;
                            measure = "Кб";
                        } else if(fileInDir.getTotalSpace() >= 8192l) {
                            countOfMeasure = fileInDir.length() / 8192l;
                            measure = "б";
                        }

                        fileSize.setText(String.valueOf(countOfMeasure) + " " + measure);

                        layoutOfFile.setOrientation(LinearLayout.HORIZONTAL);
                        layoutOfFile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("mytag", "Переходим к папке: " + fileInDir.getName());
                            }
                        });

                        layoutOfFile.addView(checkbox);
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
                layoutOfFiles.addView(layoutOfFile);
            }
        } catch (Exception e) {
            Log.d("mytag", "ошибка ассинхронной задачи");
        }

    }

}
