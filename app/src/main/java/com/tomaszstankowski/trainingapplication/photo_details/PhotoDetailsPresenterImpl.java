package com.tomaszstankowski.trainingapplication.photo_details;


import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.User;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhotoDetailsPresenterImpl implements PhotoDetailsPresenter,
        PhotoDetailsInteractor.OnPhotoChangeListener, PhotoDetailsInteractor.OnPhotoRemoveListener,
        PhotoDetailsInteractor.OnUserFetchListener {

    private static final String PHOTO = "PHOTO";
    private static final String IMAGE_URI = "IMAGE_URI";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private PhotoDetailsInteractor mInteractor;
    private PhotoDetailsView mView;
    private Photo mPhoto;
    private Uri mImage;

    @Inject
    PhotoDetailsPresenterImpl(PhotoDetailsInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onCreateView(PhotoDetailsView view) {
        mView = view;

        Intent intent = mView.getActivityContext().getIntent();
        mPhoto = intent.getParcelableExtra(PHOTO);
        mImage = intent.getParcelableExtra(IMAGE_URI);

        mInteractor.observePhoto(mPhoto.key, this);
        onPhotoChange(mPhoto);

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null && firebaseUser.getUid().equals(mPhoto.userKey)) {
            //current user is an author of the photo, we can get it's name right now
            mView.updateAuthorView(firebaseUser.getDisplayName(), true);
        } else {
            mInteractor.getUser(mPhoto.userKey, this);
        }
    }

    @Override
    public void onDestroyView() {
        mView = null;
        mInteractor.stopObservingPhoto();
    }

    @Override
    public void onPhotoChange(Photo photo) {
        mPhoto = photo;
        if (mView != null)
            mView.updatePhotoView(photo.title, photo.desc, photo.getDate(), mImage);
    }

    @Override
    public void onPhotoFetchError() {
        if (mView != null)
            mView.showMessage(PhotoDetailsView.Message.LOAD_ERROR);
    }

    @Override
    public void onPhotoRemoveSuccess() {
        if (mView != null)
            mView.finish();
    }

    @Override
    public void onPhotoRemoveFailure() {
        if (mView != null) {
            mView.showMessage(PhotoDetailsView.Message.REMOVE_ERROR);
            mView.hideProgressBar();
        }
    }

    @Override
    public void onUserFetchSuccess(User user) {
        if (mView != null)
            mView.updateAuthorView(user.name, false);
    }

    @Override
    public void onUserFetchFailure() {
        if (mView != null)
            mView.showMessage(PhotoDetailsView.Message.LOAD_ERROR);
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
}
