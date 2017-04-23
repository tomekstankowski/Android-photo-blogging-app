package com.tomaszstankowski.trainingapplication.presenter;

import android.content.Intent;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Presenter responding to PhotoCaptureFragment calls
 */

public interface PhotoCapturePresenter extends Presenter {
    void updateContent(TextView label, TextView title, SimpleDraweeView image);

    void onCaptureButtonClicked();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
