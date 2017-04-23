package com.tomaszstankowski.trainingapplication.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tomaszstankowski.trainingapplication.presenter.PhotoCapturePresenterImpl;
import com.tomaszstankowski.trainingapplication.presenter.PhotoCapturePresenter;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.presenter.Presenter;

/**
 * Fragment allowing to quickly capture photo and then save it.
 */

public class PhotoCaptureFragment extends Fragment implements PhotoCaptureView {
    private PhotoCapturePresenter mPresenter;
    private ProgressBar mProgressBar;
    private Button mCaptureButton;
    private TextView mLabel;
    private TextView mTitle;
    private SimpleDraweeView mImage;



    public PhotoCaptureFragment(){
        super();
        if (mPresenter == null)
            mPresenter = new PhotoCapturePresenterImpl();
        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_capture, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLabel = (TextView)getActivity().findViewById(R.id.fragment_photo_capture_textview_lastphoto);
        mTitle = (TextView)getActivity().findViewById(R.id.fragment_photo_capture_textview_title);
        mImage = (SimpleDraweeView)getActivity().findViewById(R.id.fragment_photo_capture_imageview_last_photo);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.fragment_photo_capture_progressbar);
        mPresenter.updateContent(mLabel,mTitle,mImage);
        setCaptureButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        animateCaptureButton();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public Context getContext(){
        return getActivity();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void notifyDataChanged(){
        mPresenter.updateContent(mLabel,mTitle,mImage);
    }

    /**
     * Results from System Camera and PhotoSaveActivity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Called in onActivityCreated()
     */
    private void setCaptureButton() {
        mCaptureButton = (Button) getActivity().findViewById(R.id.fragment_photo_capture_button);
        mCaptureButton.setOnClickListener(view -> mPresenter.onCaptureButtonClicked());
    }

    /**
     * Called in onResume()
     */
    private void animateCaptureButton() {
        Animator slideDown = AnimatorInflater.loadAnimator(getActivity(), R.animator.slide_from_top);
        Animator fadeIn = AnimatorInflater.loadAnimator(getActivity(), R.animator.fade_in);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(slideDown, fadeIn);
        set.setTarget(mCaptureButton);
        set.start();
    }
}
