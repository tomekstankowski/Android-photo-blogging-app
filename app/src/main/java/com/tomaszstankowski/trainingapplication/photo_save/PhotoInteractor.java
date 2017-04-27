package com.tomaszstankowski.trainingapplication.photo_save;

import com.tomaszstankowski.trainingapplication.model.Photo;

/**
 * Model layer saving captured photo in database and marking it as last taken
 */

public interface PhotoInteractor {

    interface OnPhotoSaveListener {
        void onSuccess();
        void onError();
    }

    void savePhoto(Photo photo, OnPhotoSaveListener listener);
}
