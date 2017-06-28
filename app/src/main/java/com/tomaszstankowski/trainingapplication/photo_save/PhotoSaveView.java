package com.tomaszstankowski.trainingapplication.photo_save;

import android.net.Uri;

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
    void updateView(String title, String desc, Uri imageUri, boolean resize);

    void showMessage(Message message);

    void showProgressBar();

    void finish();

    void finish(int resultCode);
}
