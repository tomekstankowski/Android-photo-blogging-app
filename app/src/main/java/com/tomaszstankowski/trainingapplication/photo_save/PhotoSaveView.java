package com.tomaszstankowski.trainingapplication.photo_save;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Interface implemented by PhotoSaveActivity
 */

public interface PhotoSaveView{
    void updateView(@Nullable String title, @Nullable String desc, @NonNull Uri imageUri, boolean resize);

    void showMessage(String message);

    void showProgressBar();

    Activity getActivityContext();
}
