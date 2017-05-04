package com.tomaszstankowski.trainingapplication.user_photos;

import android.net.Uri;

import com.tomaszstankowski.trainingapplication.model.Photo;

/**
 * Model layer fetching all photos taken by user from database.
 */

public interface UserPhotosInteractor {
    interface OnUserPhotosChangesListener {
        void onPhotoAdded(Photo photo, Uri image);

        void onPhotoRemoved(String key);
    }

    void addListenerForUserPhotosChanges(OnUserPhotosChangesListener listener);

    void removeListenerForUserPhotosChanges();
}
