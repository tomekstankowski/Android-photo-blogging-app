package com.tomaszstankowski.trainingapplication.discover;


import android.net.Uri;

import com.tomaszstankowski.trainingapplication.model.Photo;

public interface DiscoverInteractor {

    interface OnRecentPhotosChangeListener {
        void onPhotoAdded(Photo photo, Uri image);

        void onPhotoRemoved(String key);
    }

    void observeRecentPhotos(OnRecentPhotosChangeListener listener);

    void stopObservingRecentPhotos();
}
