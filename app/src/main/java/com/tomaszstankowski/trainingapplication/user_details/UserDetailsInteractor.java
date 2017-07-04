package com.tomaszstankowski.trainingapplication.user_details;

import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.model.Photo;

/**
 * Model layer fetching all photos taken by user from database.
 */

public interface UserDetailsInteractor {
    interface OnUserPhotosChangesListener {
        void onPhotoAdded(Photo photo, StorageReference image);

        void onPhotoRemoved(String photoKey);
    }

    void observeUserPhotos(String userKey, OnUserPhotosChangesListener listener);

    void stopObservingUserPhotos();
}
