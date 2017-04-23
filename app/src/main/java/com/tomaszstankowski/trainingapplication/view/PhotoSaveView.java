package com.tomaszstankowski.trainingapplication.view;

import android.app.Activity;
import android.content.Intent;

/**
 * Interface implemented by PhotoSaveActivity
 */

public interface PhotoSaveView extends View {
    void showProgressBar();

    void hideProgressBar();

    Intent getIntent();

    Activity getActivity();
}
