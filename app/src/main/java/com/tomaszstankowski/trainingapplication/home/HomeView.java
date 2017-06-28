package com.tomaszstankowski.trainingapplication.home;


import android.net.Uri;

import com.tomaszstankowski.trainingapplication.util.CameraException;

import java.io.File;

/**
 * View layer implemented by HomeFragment
 */

public interface HomeView {

    enum Message {
        CAMERA_ERROR,
        LOAD_ERROR
    }

    void startSystemCamera(File targeFile) throws CameraException;

    void startPhotoDetailsView();

    void startPhotoSaveView();

    void showMessage(Message message);

    void updateView(Uri imageUri);

    void clearView();
}
