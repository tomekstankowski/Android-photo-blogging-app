package com.tomaszstankowski.trainingapplication.user_photos;


import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.Map;

/**
 * View layer implemented by UserPhotosFragment
 */

interface UserPhotosView {
    void addPhoto(StorageReference image);

    void removePhoto(int position);

    void startPhotoDetailsView(Map<String, Serializable> args);

    void updateUsername(String username);
}
