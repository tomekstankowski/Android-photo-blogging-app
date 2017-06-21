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

    void observePhoto(String key, OnPhotoChangeListener listener);

    void stopObservingPhoto();

    void removePhoto(Photo photo, OnPhotoRemoveListener listener);
}
