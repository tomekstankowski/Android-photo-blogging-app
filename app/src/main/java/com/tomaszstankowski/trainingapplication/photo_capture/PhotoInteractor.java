package com.tomaszstankowski.trainingapplication.photo_capture;

import com.tomaszstankowski.trainingapplication.model.Photo;

/**
 * Model layer fetching last photo taken by user from database.
 */

public interface PhotoInteractor {
    interface OnLastPhotoFetchListener {
        void onSuccess(Photo photo);
        void onError();
    }
    void getLastPhoto(OnLastPhotoFetchListener listener);
}
