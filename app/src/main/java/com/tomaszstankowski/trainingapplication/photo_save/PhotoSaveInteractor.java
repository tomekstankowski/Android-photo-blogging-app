package com.tomaszstankowski.trainingapplication.photo_save;

import android.net.Uri;

import com.tomaszstankowski.trainingapplication.model.Photo;

/**
 * Model layer adding photo to database or editing already existing.
 */

public interface PhotoSaveInteractor {

    interface OnPhotoSaveListener {
        void onSaveSuccess();

        void onSaveError();
    }

    void editPhoto(Photo photo, OnPhotoSaveListener listener);

    void savePhoto(Photo photo, Uri imageUri, OnPhotoSaveListener listener);
}
