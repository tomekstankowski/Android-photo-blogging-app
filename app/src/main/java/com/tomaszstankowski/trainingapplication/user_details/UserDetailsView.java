package com.tomaszstankowski.trainingapplication.user_details;


import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.Map;

/**
 * View layer implemented by AbstractUserDetailsFragment
 */

interface UserDetailsView {
    void addPhoto(StorageReference image);

    void removePhoto(int position);

    void startPhotoDetailsView(Map<String, Serializable> args);

    Serializable getArg(String key);

    void updateUsername(String username);
}
