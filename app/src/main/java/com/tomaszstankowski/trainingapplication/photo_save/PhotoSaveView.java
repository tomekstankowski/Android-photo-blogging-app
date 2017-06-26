package com.tomaszstankowski.trainingapplication.photo_save;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Interface implemented by PhotoSaveActivity
 */

public interface PhotoSaveView{
    enum Message {
        ERROR
    }

    /**
     * @param title    is null when photo was just captured
     * @param desc     is null when photo was just captured
     * @param imageUri is uri of just captured uncompressed image or image downloaded from storage
     * @param resize   is true when image is uncompressed and false otherwise
     */
    void updateView(@Nullable String title, @Nullable String desc, @NonNull Uri imageUri, boolean resize);

    void showMessage(Message message);

    void showProgressBar();

    Activity getActivityContext();

    void finish();
}
