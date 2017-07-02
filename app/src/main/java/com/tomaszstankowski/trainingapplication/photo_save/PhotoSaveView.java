package com.tomaszstankowski.trainingapplication.photo_save;

import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.Serializable;

/**
 * Interface implemented by PhotoSaveActivity
 */

interface PhotoSaveView {
    enum Message {
        ERROR
    }

    Serializable getArg(String key);

    void updateEditable(String title, String desc);

    void showImage(File imageFile);

    void showImage(StorageReference image);

    void showMessage(Message message);

    void showProgressBar();

    void finish();

    void finish(int resultCode);
}
