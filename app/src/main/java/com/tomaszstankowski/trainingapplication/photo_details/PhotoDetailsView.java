package com.tomaszstankowski.trainingapplication.photo_details;


import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.Map;

/**
 * View layer implemented by PhotoDetailsFragment.
 */

interface PhotoDetailsView {
    enum Message {
        REMOVE_ERROR,
        LOAD_ERROR
    }

    void updatePhotoView(String title, String desc, String date, StorageReference image);

    void updateAuthorView(String author, boolean isAuthor);

    void startPhotoSaveView(Map<String, Serializable> args);

    void startUserView(Map<String, Serializable> args);

    Serializable getArg(String key);

    void showProgressBar();

    void hideProgressBar();

    void showMessage(Message message);

    void finish();
}
