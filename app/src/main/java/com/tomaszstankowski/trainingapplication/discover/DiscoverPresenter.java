package com.tomaszstankowski.trainingapplication.discover;

public interface DiscoverPresenter {

    void onCreateView(DiscoverView view);

    void onDestroyView();

    void onPhotoClicked(int pos);
}
