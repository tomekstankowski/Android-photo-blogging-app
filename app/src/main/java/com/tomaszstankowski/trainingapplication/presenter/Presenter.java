package com.tomaszstankowski.trainingapplication.presenter;

import com.tomaszstankowski.trainingapplication.model.Model;
import com.tomaszstankowski.trainingapplication.view.View;

/**
 * Generic presenter.
 */

public interface Presenter {
    void setModel(Model model);

    void setView(View view);

    void onDestroyView();
}
