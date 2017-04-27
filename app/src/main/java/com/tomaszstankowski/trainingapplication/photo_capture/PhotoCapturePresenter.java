package com.tomaszstankowski.trainingapplication.photo_capture;

import android.content.Context;
import android.content.Intent;


/**
 * Presenter responding to PhotoCaptureFragment calls
 */

public interface PhotoCapturePresenter{
    void onViewUpdateRequest();

    void onCaptureButtonClicked(Context context);

    void onActivityResult(Context context, int requestCode, int resultCode, Intent data);

    void onDestroyView();
}
