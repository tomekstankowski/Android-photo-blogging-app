package com.tomaszstankowski.trainingapplication.details;


import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class DetailsPresenterImpl implements DetailsPresenter {
    private DetailsView mView;

    @Inject
    DetailsPresenterImpl() {
    }

    @Override
    public void onCreateView(DetailsView view) {
        mView = view;
    }

    @Override
    public void onViewUpdateRequest() {
        Serializable mode = mView.getArg(Config.DETAILS_MODE);
        if (mode != null && mode.equals(Config.DETAILS_MODE_PHOTO)) {
            Serializable photo = mView.getArg(Config.ARG_PHOTO);
            if (photo != null && photo instanceof Photo) {
                Map<String, Serializable> args = new HashMap<>();
                args.put(Config.ARG_PHOTO, photo);
                mView.switchPage(DetailsView.Page.PHOTO, args);
            }
        } else if (mode != null && mode.equals(Config.DETAILS_MODE_USER)) {
            Serializable user = mView.getArg(Config.ARG_USER);
            if (user != null && user instanceof User) {
                Map<String, Serializable> args = new HashMap<>();
                args.put(Config.USER_DETAILS_MODE, Config.USER_DETAILS_MODE_DEFAULT);
                args.put(Config.ARG_USER, user);
                mView.switchPage(DetailsView.Page.USER, args);
            }
        }
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }
}
