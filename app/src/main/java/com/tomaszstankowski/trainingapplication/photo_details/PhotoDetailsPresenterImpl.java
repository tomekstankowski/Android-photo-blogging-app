package com.tomaszstankowski.trainingapplication.photo_details;


import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class PhotoDetailsPresenterImpl implements PhotoDetailsPresenter,
        PhotoDetailsInteractor.OnPhotoChangeListener, PhotoDetailsInteractor.OnPhotoRemoveListener {
    private static final String PHOTO = "PHOTO";
    private static final String IMAGE_URI = "IMAGE_URI";

    private PhotoDetailsInteractor mInteractor;
    private String mUserKey;
    private PhotoDetailsView mView;
    private Photo mPhoto;
    private Uri mImage;

    @Inject
    PhotoDetailsPresenterImpl(PhotoDetailsInteractor interactor, @Named("userKey") String userKey) {
        mInteractor = interactor;
        mUserKey = userKey;
    }

    @Override
    public void onCreateView(PhotoDetailsView view) {
        mView = view;
        Intent intent = mView.getActivityContext().getIntent();
        mPhoto = intent.getParcelableExtra(PHOTO);
        mImage = intent.getParcelableExtra(IMAGE_URI);
        mInteractor.observePhoto(mPhoto.key, this);
        onPhotoChange(mPhoto);
    }

    @Override
    public void onDestroyView() {
        mView = null;
        mInteractor.stopObservingPhoto();
    }

    @Override
    public void onPhotoChange(Photo photo) {
        mPhoto = photo;
        if (mView != null) {
            mView.updateView(photo.title, "admin", photo.desc, photo.getDate(), mImage,
                    photo.userKey.equals(mUserKey));
        }
    }

    @Override
    public void onPhotoFetchError() {
        mView.hideProgressBar();
    }

    @Override
    public void onEditButtonClicked() {
        Intent intent = new Intent(mView.getActivityContext(), PhotoSaveActivity.class);
        intent.putExtra(PHOTO, (Parcelable) mPhoto);
        intent.putExtra(IMAGE_URI, mImage);
        mView.startActivity(intent);
    }

    @Override
    public void onRemoveButtonClicked() {
        mInteractor.removePhoto(mPhoto, this);
        mView.showProgressBar();
    }

    @Override
    public void onPhotoRemoveSuccess() {
        mView.getActivityContext().finish();
    }

    @Override
    public void onPhotoRemoveFailure() {
        String message = mView.getActivityContext().getString(R.string.remove_error);
        mView.showMessage(message);
    }
}
