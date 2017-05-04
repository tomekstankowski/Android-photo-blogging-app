package com.tomaszstankowski.trainingapplication.photo_details;

import android.net.Uri;

import com.tomaszstankowski.trainingapplication.model.Photo;

/**
 * Model layer fetching given photo from database.
 */

public interface PhotoDetailsInteractor {
    interface OnPhotoChangeListener {
        void onPhotoChange(Photo photo, Uri image);

        void onPhotoFetchError();
    }

    void addListenerForPhotoChanges(String key, OnPhotoChangeListener listener);

    void removeListenerForPhotoChanges();
}
