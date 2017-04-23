package com.tomaszstankowski.trainingapplication.presenter;

import android.app.Activity;
import android.net.Uri;

/**
 * Presenter responding to PhotoSaveActivity calls
 */

public interface PhotoSavePresenter extends Presenter{
    Uri getImageUri();

    void onSaveButtonClicked(String title, String desc);

    void onBackButtonClicked();
}
