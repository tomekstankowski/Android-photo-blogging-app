package com.tomaszstankowski.trainingapplication.details;


import java.io.Serializable;
import java.util.Map;

public interface DetailsView {
    enum Page {
        PHOTO,
        USER
    }

    void switchPage(Page page, Map<String, Serializable> args);

    void back();

    Serializable getArg(String key);
}
