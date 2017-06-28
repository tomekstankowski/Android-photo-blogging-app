package com.tomaszstankowski.trainingapplication.photo_details;


import android.net.Uri;

/**
 * View layer implemented by PhotoDetailsActivity.
 */

public interface PhotoDetailsView {
    enum Message {
        REMOVE_ERROR,
        LOAD_ERROR
    }

    void updatePhotoView(String title, String desc, String date, Uri image);

    void updateAuthorView(String author, boolean isAuthor);

    void startPhotoSaveView();

    void showProgressBar();

    void hideProgressBar();

    void showMessage(Message message);

    void finish();
}
