package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    public LinearLayout layoutOfFiles;
    public LinearLayout operations;
    public ArrayList<HashMap<String, Object>> myFiles;
    public String measure = "Мб";
    public Long countOfMeasure = 0l;
    public boolean checkboxesVisible = false;
    public String filesType = "none";
    public String supportedExtensions = "";
    public String currentPath;
    public String extension = "folder";
    public ArrayList<File> currentFiles;
    public ArrayList<CheckBox> selectedCheckBoxes;
    public ArrayList<Integer> selectedFiles;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            filesType = extras.getString("filesType");
            currentPath = extras.getString("currentPath");

            TextView currentPathSeparator = findViewById(R.id.currentPath);
//            int resultOfSeparator = currentPathSeparator.getText().toString().lastIndexOf('/');
//            String lastFolder = "Изображения";
//            String lastFolder = "abc";
//            if (resultOfSeparator > 0) {
//                lastFolder = currentPathSeparator.getText().toString().substring(resultOfSeparator + 1);
//            }
//            currentPathSeparator.setText(lastFolder);
            currentPathSeparator.setText(currentPath.split("/")[currentPath.split("/").length - 1]);

            if(filesType.contains("image")){
                supportedExtensions += " " + "png jpg jpeg gif tiff tga psd ai";
            } else if(filesType.contains("document")){
                supportedExtensions += " " + "txt doc pdf";
            } else if(filesType.contains("video")){
                supportedExtensions += " " + "mpeg4 mp4 flv dooh";
            } else if(filesType.contains("audio")){
                supportedExtensions += " " + "mpeg3 mp3 wav ogg";
            } else if(filesType.contains("download")){
                supportedExtensions += " " + "torrent";
            } else if(filesType.contains("apk")){
                supportedExtensions += " " + "apk";
            }
        }

        operations = findViewById(R.id.operations);
        ImageButton moveBtn = findViewById(R.id.moveBtn);
        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mytag", "перемещаю");
            }
        });
        ImageButton copyBtn = findViewById(R.id.copyBtn);
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("mytag", "Копирую");
//                Log.d("mytag", "индекс: " + );
                for(CheckBox selectedCheckBox : selectedCheckBoxes){
                    Integer layoutIdx = selectedCheckBoxes.indexOf(selectedCheckBox);
                    if(selectedCheckBox.isChecked()){
                        Log.d("mytag", "подходит индекс: " + currentFiles.get(layoutIdx).getName());
                    } else {
                        Log.d("mytag", "неподходит индекс: " + currentFiles.get(layoutIdx).getName());
                    }
                }
            }
        });
        ImageButton infoBtn = findViewById(R.id.infoBtn);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("mytag", "даю инфо");
                for(CheckBox selectedCheckBox : selectedCheckBoxes){
                    Integer layoutIdx = selectedCheckBoxes.indexOf(selectedCheckBox);
                    if(selectedCheckBox.isChecked()){
                        Log.d("mytag", "подходит индекс: " + currentFiles.get(layoutIdx).getName());
//                        String toastMessage = "Свойства \n" + "\nИмя \n" + currentFiles.get(Integer.valueOf(v.getContentDescription().toString())).getName().toString() + "размер \n" + currentFiles.get(Integer.valueOf(v.getContentDescription().toString())).length() + "\nПоследнее изменение \n" + currentFiles.get(Integer.valueOf(v.getContentDescription().toString())).getPath().toString() + "\nПуть \n" + currentFiles.get(Integer.valueOf(v.getContentDescription().toString())).getPath().toString();
                        String toastMessage = "Свойства \n" + "\nИмя \n" + currentFiles.get(layoutIdx).getName().toString() + "\nразмер \n" + currentFiles.get(layoutIdx).length() + "\nПоследнее изменение \n" + new Date(currentFiles.get(layoutIdx).lastModified() * 1000).getYear() + "\nПуть \n" + currentFiles.get(layoutIdx).getPath().toString();
                        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Log.d("mytag", "неподходит индекс: " + currentFiles.get(layoutIdx).getName());
                    }
                }
            }
        });
        ImageButton shareBtn = findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mytag", "даю доступ");
            }
        });
        ImageButton removeBtn = findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mytag", "удаляю");
//                .delete();
//                LinearLayout currentLayout = (LinearLayout) layoutOfFiles.getChildAt(Integer.valueOf(v.getContentDescription().toString()));
                currentFiles.get(Integer.valueOf(v.getContentDescription().toString())).delete();
            }
        });

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
        layoutOfFiles = findViewById(R.id.layoutOfFiles);

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
            Log.d("mytag", "currentPath: " + currentPath);
//            listOfFiles = new FileTask(getApplicationContext()).execute("url").get();
            listOfFiles = new FileTask().execute(currentPath).get();

            if(listOfFiles.length >= 1) {
                Log.d("mytag", "файлов много");

                selectedCheckBoxes = new ArrayList<>();
                currentFiles = new ArrayList<>();
//                selectedFiles = new ArrayList<>();

                int cursorOfFiles = -1;
//                for(File fileInDir : getApplicationContext().getCacheDir().listFiles()){
                for(File fileInDir : listOfFiles){
                    Log.d("mytag", "имя файла: " + fileInDir.getName() + "размер файла: " + fileInDir.getTotalSpace());

                    int resultOfExtension = fileInDir.getName().lastIndexOf('.');
                    if (resultOfExtension > 0) {
                        extension = fileInDir.getName().substring(resultOfExtension + 1);
                    }
                    Log.d("mytag", "Расширение файла: " + extension);

                    if(supportedExtensions.contains(extension) || extension.contains("folder")) {
                        LinearLayout layoutOfFile = new LinearLayout(ListActivity.this);
                        currentFiles.add(fileInDir);
                        cursorOfFiles++;
                        layoutOfFile.setContentDescription(String.valueOf(cursorOfFiles));
                        Log.d("mytag", "layoutOfFile.getContentDescription(): " + layoutOfFile.getContentDescription());
                        ConstraintLayout.LayoutParams fileLayoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
//                        ConstraintLayout.LayoutParams fileLayoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, 125);
//                        fileLayoutParams.setMargins(5,75,5, 75);
                        fileLayoutParams.setMargins(5, 15, 5, 15);
                        layoutOfFile.setLayoutParams(fileLayoutParams);

                        CheckBox checkbox = new CheckBox(ListActivity.this);
                        checkbox.setText("");
                        checkbox.setVisibility(View.INVISIBLE);
                        selectedCheckBoxes.add(checkbox);
                        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                for(CheckBox selectedCheckBox : selectedCheckBoxes){
                                    if(String.valueOf(selectedCheckBox.getId()).contains(String.valueOf(buttonView.getId()))){
//                                        = isChecked;
                                    }
                                }
                            }
                        });

                        ImageView fileIcon = new ImageView(ListActivity.this);
                        fileIcon.setImageResource(R.drawable.home);
                        fileIcon.setContentDescription(extension);

//                        if(Files.probeContentType(fileInDir.toPath()).contains("image/")) {
//                            fileIcon.setImageResource(R.drawable.image);
//                        } else if(Files.probeContentType(fileInDir.toPath()).contains("application/octet")) {
//                            fileIcon.setImageResource(R.drawable.download);
//                        } else if(Files.probeContentType(fileInDir.toPath()).contains("application/vnd.android.package")) {
//                            fileIcon.setImageResource(R.drawable.apkfile);
//                        } else if(Files.probeContentType(fileInDir.toPath()).contains("audio/")) {
//                            fileIcon.setImageResource(R.drawable.music);
//                        } else if(Files.probeContentType(fileInDir.toPath()).contains("video/")) {
//                            fileIcon.setImageResource(R.drawable.video);
//                        } else if(Files.probeContentType(fileInDir.toPath()).contains("text/")) {
//                            fileIcon.setImageResource(R.drawable.document);
//                        } else {
//                            fileIcon.setImageResource(R.drawable.home);
//                        }

                        if(extension.contains("png") || extension.contains("jpg") || extension.contains("jpeg") || extension.contains("gif")) {
                            fileIcon.setImageResource(R.drawable.image);
                        } else if(extension.contains("torrent")) {
                            fileIcon.setImageResource(R.drawable.download);
                        } else if(extension.contains("apk")) {
                            fileIcon.setImageResource(R.drawable.apkfile);
                        } else if(extension.contains("mp3") || extension.contains("wav") || extension.contains("ogg")) {
                            fileIcon.setImageResource(R.drawable.music);
                        } else if(extension.contains("mp4") || extension.contains("flv") || extension.contains("dooh")) {
                            fileIcon.setImageResource(R.drawable.video);
                        } else if(extension.contains("txt") || extension.contains("doc") || extension.contains("pdf")) {
                            fileIcon.setImageResource(R.drawable.document);
                        } else {
                            fileIcon.setImageResource(R.drawable.home);
                        }
//                        fileIcon.setImageResource(R.drawable.home);

                        fileIcon.setImageTintList(ColorStateList.valueOf(Color.argb(100, 145, 145, 145)));
                        fileIcon.setLayoutParams(fileLayoutParams);
                        fileIcon.setScaleType(ImageView.ScaleType.FIT_START);
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
                        if (fileInDir.length() >= 8796093022208l) {
                            countOfMeasure = fileInDir.length() / 8796093022208l;
                            measure = "Гб";
                        } else if (fileInDir.length() >= 8589934592l) {
                            countOfMeasure = fileInDir.length() / 8589934592l;
                            measure = "Мб";
                        } else if (fileInDir.length() >= 8388608l) {
                            countOfMeasure = fileInDir.length() / 8388608l;
                            measure = "Кб";
                        } else if (fileInDir.getTotalSpace() >= 8192l) {
                            countOfMeasure = fileInDir.length() / 8192l;
                            measure = "б";
                        }

                        fileSize.setText(String.valueOf(countOfMeasure) + " " + measure);

                        layoutOfFile.setOrientation(LinearLayout.HORIZONTAL);
                        layoutOfFile.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(View v) {
                                if(!checkboxesVisible){
                                    LinearLayout currentLayout = (LinearLayout) layoutOfFiles.getChildAt(Integer.valueOf(v.getContentDescription().toString()));
                                    if(currentLayout.getChildAt(1).getContentDescription().toString().contains("folder")) {
                                        Log.d("mytag", "Переходим к папке: " + fileInDir.getName());
                                        Intent intent = new Intent(ListActivity.this, ListActivity.class);
//                                        intent.putExtra("filesType", "none");
                                        intent.putExtra("filesType", filesType);
                                        intent.putExtra("currentPath", currentPath + "/" + fileInDir.getName());
                                        ListActivity.this.startActivity(intent);
                                    } else {
                                        Log.d("mytag", "Открываем файл: " + fileInDir.getName());

                                        try {
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setDataAndType(Uri.fromFile(fileInDir), Files.probeContentType(Paths.get(fileInDir.getPath())));
                                            startActivity(intent);
                                        } catch (ActivityNotFoundException | IOException e) {
                                            Log.d("mytag", "Не получилось открыть файл: " + fileInDir.getName());
                                        }

                                    }
                                }
                            }
                        });

                        layoutOfFile.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                            @Override
                            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

//                                menu.add(Menu.NONE, 101, Menu.NONE, "Открыть");
//                                menu.add(Menu.NONE, 102, Menu.NONE, "Сохранить");

                                operations.setVisibility(View.VISIBLE);
                                checkboxesVisible = true;
                                for (int fileCheckboxIdx = 0; fileCheckboxIdx < layoutOfFiles.getChildCount(); fileCheckboxIdx++) {
                                    LinearLayout localLayout = (LinearLayout) layoutOfFiles.getChildAt(fileCheckboxIdx);
                                    localLayout.getChildAt(0).setVisibility(View.VISIBLE);
                                    if (localLayout.getContentDescription().toString().contains(v.getContentDescription())) {
                                        CheckBox localCheckbox = (CheckBox) localLayout.getChildAt(0);
                                        localCheckbox.setChecked(true);
                                    }
                                }

                            }

                        });

                        layoutOfFile.addView(checkbox);
                        layoutOfFile.addView(fileIcon);
                        layoutOfFile.addView(fileName);
                        layoutOfFile.addView(fileSize);
                        layoutOfFiles.addView(layoutOfFile);
                    }
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

        Log.d("mytag", "currentFiles: " + String.valueOf(currentFiles.size()));

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(Menu.NONE, Integer.parseInt("Выбран пункт Открыть"), Menu.NONE, "Открыть");
        menu.add(Menu.NONE, Integer.parseInt("Выбран пункт Сохранить"), Menu.NONE, "Сохранить");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d("mytag", String.valueOf(item.getItemId()));
        return true;
    }

    @Override
    public void onBackPressed() {
        if(checkboxesVisible) {
            operations.setVisibility(View.INVISIBLE);
            checkboxesVisible = true;
            for (int fileCheckboxIdx = 0; fileCheckboxIdx < layoutOfFiles.getChildCount(); fileCheckboxIdx++) {
                LinearLayout localLayout = (LinearLayout) layoutOfFiles.getChildAt(fileCheckboxIdx);
                CheckBox localCheckbox = (CheckBox) localLayout.getChildAt(0);
                localLayout.getChildAt(0).setVisibility(View.INVISIBLE);
                localCheckbox.setChecked(false);
            }
            checkboxesVisible = false;
        } else {
            super.onBackPressed();
        }
    }

}
