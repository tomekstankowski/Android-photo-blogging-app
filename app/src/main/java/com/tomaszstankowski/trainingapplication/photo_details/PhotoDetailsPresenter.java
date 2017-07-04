package com.tomaszstankowski.trainingapplication.photo_details;

/**
 * Presenter responding to PhotoDetailsFragment calls.
 */

public interface PhotoDetailsPresenter {

    void onCreateView(PhotoDetailsView view);

    void onUserClicked();

    void onEditButtonClicked();

    void onRemoveButtonClicked();

    void onDestroyView();
}
