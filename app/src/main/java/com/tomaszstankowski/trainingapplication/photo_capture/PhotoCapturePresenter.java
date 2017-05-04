package com.tomaszstankowski.trainingapplication.photo_capture;

import android.content.Intent;


/**
 * Presenter responding to PhotoCaptureFragment calls
 */

public interface PhotoCapturePresenter{
    void onCreateView(PhotoCaptureView view);

    void onCaptureButtonClicked();

    void onImageClicked();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onDestroyView();
}
