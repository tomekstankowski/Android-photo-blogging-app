package com.tomaszstankowski.trainingapplication.user_photos;

/**
 * Presenter responding to UserPhotosFragment calls.
 */

public interface UserPhotosPresenter {
    void onCreateView(UserPhotosView view);

    void onPhotoClicked(int position);

    void onDestroyView();
}
