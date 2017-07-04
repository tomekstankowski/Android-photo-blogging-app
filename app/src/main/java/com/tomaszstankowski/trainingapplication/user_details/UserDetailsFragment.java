package com.tomaszstankowski.trainingapplication.user_details;

import android.os.Bundle;

import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.details.DetailsView;
import com.tomaszstankowski.trainingapplication.ui.GalleryViewAdapter;

import java.io.Serializable;
import java.util.Map;

public class UserDetailsFragment extends AbstractUserDetailsFragment implements UserDetailsView, GalleryViewAdapter.OnItemClickListener {

    private DetailsView mParent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getMainComponent().inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            mParent = (DetailsView) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement DetailsView");
        }
    }

    @Override
    public void startPhotoDetailsView(Map<String, Serializable> args) {
        mParent.switchPage(DetailsView.Page.PHOTO, args);
    }
}
