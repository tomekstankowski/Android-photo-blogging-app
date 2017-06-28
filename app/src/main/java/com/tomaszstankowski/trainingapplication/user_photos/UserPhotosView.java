package com.tomaszstankowski.trainingapplication.user_photos;

import android.net.Uri;

/**
 * View layer implemented by UserPhotosFragment
 */

public interface UserPhotosView {
    void addPhoto(Uri uri);

    void removePhoto(int position);

    void removeAllPhotos();

    void startPhotoDetailsView();

    void updateUsername(String username);
}
