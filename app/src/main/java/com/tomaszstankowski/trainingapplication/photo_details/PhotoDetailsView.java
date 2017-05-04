package com.tomaszstankowski.trainingapplication.photo_details;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * View layer implemented by PhotoDetailsActivity.
 */

public interface PhotoDetailsView {
    void updateView(String title, String author, String desc, String date, Uri image, boolean isEditable);

    void startActivity(Intent intent);

    Activity getActivityContext();

    void showProgressBar();

    void hideProgressBar();
}
