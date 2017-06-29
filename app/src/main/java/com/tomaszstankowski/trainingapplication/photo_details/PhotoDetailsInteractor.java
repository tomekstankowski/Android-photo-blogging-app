package com.tomaszstankowski.trainingapplication.photo_details;

import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.User;

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

    interface OnUserFetchListener {
        void onUserFetchSuccess(User user);

        void onUserFetchFailure();
    }

    void observePhoto(String key, OnPhotoChangeListener listener);

    void stopObservingPhoto();

    void removePhoto(Photo photo, OnPhotoRemoveListener listener);

    void getUser(String key, OnUserFetchListener listener);

    StorageReference getImage(Photo photo);
}
