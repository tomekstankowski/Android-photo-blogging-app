package com.tomaszstankowski.trainingapplication.photo_save;

import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.model.Photo;

import java.io.InputStream;

/**
 * Model layer adding photo to database or editing already existing.
 */

public interface PhotoSaveInteractor {

    interface OnPhotoSaveListener {
        void onSaveSuccess();
        void onSaveError();
    }

    void editPhoto(Photo photo, OnPhotoSaveListener listener);

    void savePhoto(Photo photo, InputStream imageInputStream, OnPhotoSaveListener listener);

    StorageReference getImage(Photo photo);
}
