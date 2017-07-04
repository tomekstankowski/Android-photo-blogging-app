package com.tomaszstankowski.trainingapplication.details;


public interface DetailsPresenter {
    void onCreateView(DetailsView view);

    void onDestroyView();

    void onViewUpdateRequest();
}
