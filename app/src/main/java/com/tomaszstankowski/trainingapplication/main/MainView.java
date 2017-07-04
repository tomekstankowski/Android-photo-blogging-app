package com.tomaszstankowski.trainingapplication.main;

import java.io.Serializable;
import java.util.Map;

interface MainView {
    enum Navigable {
        HOME,
        DISCOVER,
        MY_PROFILE,
        SETTINGS
    }

    void startLoginView(int mode);

    void navigate(Navigable view, Map<String, Serializable> args);
}
