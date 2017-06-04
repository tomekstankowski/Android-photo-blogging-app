package com.tomaszstankowski.trainingapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.zelory.compressor.Compressor;

/**
 * Support class performing operations on image.
 */

public class PhotoService {
    private Context mContext;

    public PhotoService(Context context) {
        mContext = context;
    }

    public File createPhotoFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public void addPhotoToGallery(File photoFile) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photoFile);
        mediaScanIntent.setData(contentUri);
        mContext.sendBroadcast(mediaScanIntent);
    }

    public Uri getUriFromFile(File file) {
        if(file != null)
            return FileProvider.getUriForFile(mContext, "com.example.android.fileprovider", file);
        return null;
    }

    public Uri compressImage(String path) {
        File realImageFile = new File(path);
        File compressedImageFile = Compressor.getDefault(mContext).compressToFile(realImageFile);
        //somehow the method getUriFromFile doesn't work in this case (although it is recommended)
        return Uri.fromFile(compressedImageFile);
    }


}
