package com.tomaszstankowski.trainingapplication.photo_details;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.event.PhotoTransferEvent;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private StorageReference mImage;

    @Inject
    PhotoDetailsPresenterImpl(PhotoDetailsInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onCreateView(PhotoDetailsView view) {
        mView = view;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        mView = null;
        EventBus.getDefault().unregister(this);
        mInteractor.stopObservingPhoto();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onPhotoTransferEvent(PhotoTransferEvent event) {
        if (event.requestCode == Config.RC_PHOTO_DETAILS) {
            mPhoto = event.photo;
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

            EventBus.getDefault().removeStickyEvent(event);
        }
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
        mView.startPhotoSaveView();
        EventBus.getDefault().postSticky(new PhotoTransferEvent(
                mPhoto, Config.RC_PHOTO_SAVE)
        );
    }

    @Override
    public void onRemoveButtonClicked() {
        mInteractor.removePhoto(mPhoto, this);
        mView.showProgressBar();
    }
}
