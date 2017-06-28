package com.tomaszstankowski.trainingapplication.util;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;

public class FileUtil {
    private Context mContext;

    public FileUtil(Context context) {
        mContext = context;
    }

    public Uri getUriFromFile(File file) {
        return FileProvider.getUriForFile(
                mContext,
                "com.example.android.fileprovider",
                file);
    }
}
