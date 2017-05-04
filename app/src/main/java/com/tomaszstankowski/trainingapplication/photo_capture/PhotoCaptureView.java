package com.tomaszstankowski.trainingapplication.photo_capture;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * View layer implemented by PhotoCaptureFragment
 */

public interface PhotoCaptureView{
    void startActivityForResult(Intent intent, int requestCode);

    void startActivity(Intent intent);

    Context getContext();

    void showMessage(String message);

    void showProgressBar();

    void hideProgressBar();

    void updateView(Uri imageUri);
}
