package com.tomaszstankowski.trainingapplication.user_details;

/**
 * Presenter responding to AbstractUserDetailsFragment calls.
 */

public interface UserDetailsPresenter {
    void onCreateView(UserDetailsView view);

    void onPhotoClicked(int position);

    void onDestroyView();
}
