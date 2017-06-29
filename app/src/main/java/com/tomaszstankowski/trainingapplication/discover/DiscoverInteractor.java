package com.tomaszstankowski.trainingapplication.discover;

import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.model.Photo;

public interface DiscoverInteractor {

    interface OnRecentPhotosChangeListener {
        void onPhotoAdded(Photo photo, StorageReference image);

        void onPhotoRemoved(String key);
    }

    void observeRecentPhotos(OnRecentPhotosChangeListener listener);

    void stopObservingRecentPhotos();
}
