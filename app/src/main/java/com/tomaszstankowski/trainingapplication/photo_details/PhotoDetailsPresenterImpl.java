package com.tomaszstankowski.trainingapplication.photo_details;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhotoDetailsPresenterImpl implements PhotoDetailsPresenter,
        PhotoDetailsInteractor.OnPhotoChangeListener, PhotoDetailsInteractor.OnPhotoRemoveListener,
        PhotoDetailsInteractor.OnUserFetchListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private PhotoDetailsInteractor mInteractor;
    private PhotoDetailsView mView;
    private Photo mPhoto;
    private User mUser;
    private StorageReference mImage;

    @Inject
    PhotoDetailsPresenterImpl(PhotoDetailsInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onCreateView(PhotoDetailsView view) {
        mView = view;
        Serializable photo = mView.getArg(Config.ARG_PHOTO);
        if (photo != null && photo instanceof Photo) {
            mPhoto = (Photo) photo;
            mInteractor.observePhoto(mPhoto.key, this);
            mImage = mInteractor.getImage(mPhoto);
            onPhotoChange(mPhoto);

            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if (firebaseUser != null && firebaseUser.getUid().equals(mPhoto.userKey)) {
                //current user is an author of the photo, we can get it's name right now
                mView.updateAuthorView(firebaseUser.getDisplayName(), true);
            } else {
                mInteractor.getUser(mPhoto.userKey, this);
            }
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
        mUser = user;
        if (mView != null)
            mView.updateAuthorView(user.name, false);
    }

    @Override
    public void onUserFetchFailure() {
        mUser = null;
        if (mView != null)
            mView.showMessage(PhotoDetailsView.Message.LOAD_ERROR);
    }

    @Override
    public void onUserClicked() {
        if (mUser != null) {
            Map<String, Serializable> args = new HashMap<>();
            args.put(Config.ARG_USER, mUser);
            mView.startUserView(args);
        }
    }

    @Override
    public void onEditButtonClicked() {
        Map<String, Serializable> args = new HashMap<>();
        args.put(Config.ARG_PHOTO, mPhoto);
        mView.startPhotoSaveView(args);
    }

    @Override
    public void onRemoveButtonClicked() {
        mInteractor.removePhoto(mPhoto, this);
        mView.showProgressBar();
    }
}
