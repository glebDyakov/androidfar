package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;

public class FileTask extends AsyncTask<String, Integer, File[]> {

    public Context context;

    public FileTask() {

    }
    public FileTask(Context context) {
        this.context = context;
    }

    @Override
    protected File[] doInBackground(String... paths) {
//        File[] files = context.getCacheDir().listFiles();
        File[] files = new File(paths[0]).listFiles();
//        File[] files = Files (paths[0]).listFiles();
//        File[] files = context.getFilesDir().listFiles();

        return files;
    }

    protected void onPostExecute(File[] result) {

    }

}
