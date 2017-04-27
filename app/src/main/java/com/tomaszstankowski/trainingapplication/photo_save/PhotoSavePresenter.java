package com.tomaszstankowski.trainingapplication.photo_save;

import android.app.Activity;
import android.net.Uri;

/**
 * Presenter responding to PhotoSaveActivity calls
 */

public interface PhotoSavePresenter{
    Uri getImageUri(Activity activity);

    void onSaveButtonClicked(Activity activity, String title, String desc);

    void onBackButtonClicked(Activity activity);

    void onDestroyView();
}
