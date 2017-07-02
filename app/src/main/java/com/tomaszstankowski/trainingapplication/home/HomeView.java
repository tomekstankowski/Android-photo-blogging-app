package com.tomaszstankowski.trainingapplication.home;


import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.util.CameraException;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

/**
 * View layer implemented by HomeFragment
 */

interface HomeView {

    enum Message {
        CAMERA_ERROR,
        LOAD_ERROR
    }

    void startSystemCamera(File targeFile) throws CameraException;

    void startPhotoDetailsView(Map<String, Serializable> args);

    void startPhotoSaveViewForResult(Map<String, Serializable> args);

    void showMessage(Message message);

    void updateView(StorageReference image);

    void clearView();
}
