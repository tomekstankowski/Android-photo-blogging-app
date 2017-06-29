package com.tomaszstankowski.trainingapplication.user_photos;


import com.google.firebase.storage.StorageReference;

/**
 * View layer implemented by UserPhotosFragment
 */

interface UserPhotosView {
    void addPhoto(StorageReference image);

    void removePhoto(int position);

    void startPhotoDetailsView();

    void updateUsername(String username);
}
