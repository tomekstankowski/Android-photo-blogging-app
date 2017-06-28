package com.tomaszstankowski.trainingapplication.photo_details;

/**
 * Presenter responding to PhotoDetailsActivity calls.
 */

public interface PhotoDetailsPresenter {

    void onCreateView(PhotoDetailsView view);

    void onEditButtonClicked();

    void onRemoveButtonClicked();

    void onDestroyView();
}
