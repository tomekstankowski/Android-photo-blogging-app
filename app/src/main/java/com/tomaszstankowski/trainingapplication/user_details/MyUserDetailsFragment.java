package com.tomaszstankowski.trainingapplication.user_details;


import android.os.Bundle;

import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.details.DetailsActivity;

import java.io.Serializable;
import java.util.Map;

public class MyUserDetailsFragment extends AbstractUserDetailsFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getMainComponent().inject(this);
    }

    @Override
    public void startPhotoDetailsView(Map<String, Serializable> args) {
        args.put(Config.DETAILS_MODE, Config.DETAILS_MODE_PHOTO);
        DetailsActivity.start(getContext(), args);
    }

}
