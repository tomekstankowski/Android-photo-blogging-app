package com.tomaszstankowski.trainingapplication.photo_save;

/**
 * Presenter responding to PhotoSaveActivity calls
 */

public interface PhotoSavePresenter{
    void onCreateView(PhotoSaveView view);

    void onSaveButtonClicked(String title, String desc);

    void onBackButtonClicked();

    void onDestroyView();
}
