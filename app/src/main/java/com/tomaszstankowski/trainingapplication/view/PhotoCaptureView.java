package com.tomaszstankowski.trainingapplication.view;

import android.content.Intent;

/**
 * Interface implemented by PhotoCaptureFragment
 */

public interface PhotoCaptureView extends View {
    void startActivityForResult(Intent intent, int requestCode);

    void showProgressBar();

    void hideProgressBar();

    void notifyDataChanged();
}
