package com.tomaszstankowski.trainingapplication.photo_details;

import com.tomaszstankowski.trainingapplication.model.Photo;

/**
 * Model layer fetching given photo from database.
 */

public interface PhotoDetailsInteractor {
    interface OnPhotoChangeListener {
        void onPhotoChange(Photo photo);
        void onPhotoFetchError();
    }

    interface OnPhotoRemoveListener {
        void onPhotoRemoveSuccess();

        void onPhotoRemoveFailure();
    }

    void addListenerForPhotoChanges(String key, OnPhotoChangeListener listener);

    void removeListenerForPhotoChanges();

    void removePhoto(Photo photo, OnPhotoRemoveListener listener);
}
