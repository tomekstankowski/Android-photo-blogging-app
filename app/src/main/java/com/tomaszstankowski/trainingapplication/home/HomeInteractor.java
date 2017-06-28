package com.tomaszstankowski.trainingapplication.home;

import android.net.Uri;

import com.tomaszstankowski.trainingapplication.model.Photo;

/**
 * Model layer fetching last photo taken by user from database.
 */

public interface HomeInteractor {
    interface OnLastPhotoChangeListener {
        void onLastPhotoChanged(Photo photo, Uri imageUri);

        void onLastPhotoRemoved();

        void onLastPhotoFetchError();
    }

    void observeUserLastPhoto(String userKey, OnLastPhotoChangeListener listener);

    void stopObservingUserLastPhoto();
}
