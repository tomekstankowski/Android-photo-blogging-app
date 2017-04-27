package com.tomaszstankowski.trainingapplication.photo_capture;

import android.content.Intent;
import android.net.Uri;

/**
 * View layer implemented by PhotoCaptureFragment
 */

public interface PhotoCaptureView{
    void startActivityForResult(Intent intent, int requestCode);

    void showMessage(String message);

    void showProgressBar();

    void hideProgressBar();

    void updateView(String title, Uri imageUri);
}
