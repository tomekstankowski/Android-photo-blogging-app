package com.tomaszstankowski.trainingapplication.user_photos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * View layer implemented by UserPhotosFragment
 */

public interface UserPhotosView {
    void addPhoto(Uri uri);

    void removePhoto(int position);

    void startActivity(Intent intent);

    Context getContext();

    void showProgressBar();

    void hideProgressBar();
}
