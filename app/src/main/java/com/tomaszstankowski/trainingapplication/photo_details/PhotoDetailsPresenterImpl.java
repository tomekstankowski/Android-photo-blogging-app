package com.tomaszstankowski.trainingapplication.photo_details;


import android.content.Intent;
import android.net.Uri;

import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;

public class PhotoDetailsPresenterImpl implements PhotoDetailsPresenter, PhotoDetailsInteractor.OnPhotoChangeListener {
    private PhotoDetailsView mView;
    private PhotoDetailsInteractor mInteractor;
    private static final String PHOTO_KEY = "PHOTO_KEY";
    private static final String PHOTO = "PHOTO";
    private static final String IMAGE_URI = "IMAGE_URI";
    private Photo mPhoto;
    private Uri mImage;

    public PhotoDetailsPresenterImpl() {
        mInteractor = new PhotoDetailsInteractorImpl();
    }

    @Override
    public void onCreateView(PhotoDetailsView view) {
        mView = view;
        Intent intent = mView.getActivityContext().getIntent();
        String key = intent.getStringExtra(PHOTO_KEY);
        mInteractor.addListenerForPhotoChanges(key, this);
        mView.showProgressBar();
    }

    @Override
    public void onDestroyView() {
        mView = null;
        mInteractor.removeListenerForPhotoChanges();
    }

    @Override
    public void onPhotoChange(Photo photo, Uri image) {
        mPhoto = photo;
        mImage = image;
        if (mView != null) {
            mView.updateView(photo.title, "admin", photo.desc, photo.getDate(), image, photo.userKey.equals("admin"));
            mView.hideProgressBar();
        }
    }

    @Override
    public void onPhotoFetchError() {
        mView.hideProgressBar();
    }

    @Override
    public void onEditButtonClicked() {
        Intent intent = new Intent(mView.getActivityContext(), PhotoSaveActivity.class);
        intent.putExtra(PHOTO, mPhoto);
        intent.putExtra(IMAGE_URI, mImage);
        mView.startActivity(intent);
    }
}
