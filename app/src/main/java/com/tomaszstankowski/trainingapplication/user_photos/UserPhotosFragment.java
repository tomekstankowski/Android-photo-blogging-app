package com.tomaszstankowski.trainingapplication.user_photos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tomaszstankowski.trainingapplication.R;

/**
 * Fragment showing all photos of the given user.
 */

public class UserPhotosFragment extends Fragment implements UserPhotosView {
    private UserPhotosPresenter mPresenter;

    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter == null)
            mPresenter = new UserPhotosPresenterImpl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_photos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.fragment_user_photos_progressbar);
        mPresenter.onCreateView(this);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void addPhoto(Uri uri) {

    }

    @Override
    public void removePhoto(int position) {

    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
