package com.tomaszstankowski.trainingapplication.photo_details;


import com.google.firebase.storage.StorageReference;

/**
 * View layer implemented by PhotoDetailsActivity.
 */

interface PhotoDetailsView {
    enum Message {
        REMOVE_ERROR,
        LOAD_ERROR
    }

    void updatePhotoView(String title, String desc, String date, StorageReference image);

    void updateAuthorView(String author, boolean isAuthor);

    void startPhotoSaveView();

    void showProgressBar();

    void hideProgressBar();

    void showMessage(Message message);

    void finish();
}
