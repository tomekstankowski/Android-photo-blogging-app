package com.tomaszstankowski.trainingapplication.home;

import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.model.Photo;

/**
 * Model layer fetching last photo taken by user from database.
 */

public interface HomeInteractor {
    interface OnLastPhotoChangeListener {
        void onLastPhotoChanged(Photo photo, StorageReference image);

        void onLastPhotoRemoved();

        void onLastPhotoFetchError();
    }

    void observeUserLastPhoto(String userKey, OnLastPhotoChangeListener listener);

    void stopObservingUserLastPhoto();
}
