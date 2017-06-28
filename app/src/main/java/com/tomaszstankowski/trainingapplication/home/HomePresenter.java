package com.tomaszstankowski.trainingapplication.home;

/**
 * Presenter responding to HomeFragment calls
 */

public interface HomePresenter {
    void onCreateView(HomeView view);

    void onCaptureButtonClicked();

    void onImageClicked();

    void onSystemCameraResult(int resultCode);

    void onPhotoSaveViewResult(int resultCode);

    void onDestroyView();
}
