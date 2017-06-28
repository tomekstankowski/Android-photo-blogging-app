package com.tomaszstankowski.trainingapplication.photo_save;


import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.event.PhotoTransferEvent;
import com.tomaszstankowski.trainingapplication.event.TempImageFileTransferEvent;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.util.FileUtil;
import com.tomaszstankowski.trainingapplication.util.ImageManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Presenter responding to PhotoSaveActivity calls.
 */
@Singleton
public class PhotoSavePresenterImpl implements PhotoSavePresenter, PhotoSaveInteractor.OnPhotoSaveListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private PhotoSaveInteractor mInteractor;
    private ImageManager mManager;
    private FileUtil mFileUtil;
    private PhotoSaveView mView;
    private Photo mPhoto;
    private File mTempImageFile;

    @Inject
    PhotoSavePresenterImpl(PhotoSaveInteractor interactor, ImageManager manager, FileUtil fileUtil) {
        mInteractor = interactor;
        mManager = manager;
        mFileUtil = fileUtil;
    }

    @Override
    public void onCreateView(PhotoSaveView view) {
        mView = view;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        mView = null;
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onPhotoEditEvent(PhotoTransferEvent event) {
        if (event.requestCode == Config.RC_PHOTO_SAVE) {
            mPhoto = event.photo;
            Uri image = event.image;
            mView.updateView(mPhoto.title, mPhoto.desc, image, false);
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onPhotoSaveEvent(TempImageFileTransferEvent event) {
        if (event.requestCode == Config.RC_PHOTO_SAVE) {
            mPhoto = null;
            mTempImageFile = event.file;
            Uri image = mFileUtil.getUriFromFile(mTempImageFile);
            mView.updateView(null, null, image, true);
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Override
    public void onSaveButtonClicked(String title, String desc) {
        //saving captured photo
        if (mPhoto == null) {
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if (firebaseUser != null) {
                mPhoto = new Photo(title, desc, firebaseUser.getUid());
                Single.fromCallable(() -> {
                    File compressedImageFile = mManager.compressImage(mTempImageFile);
                    return Uri.fromFile(compressedImageFile);
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                imageUri -> mInteractor.savePhoto(
                                        mPhoto,
                                        imageUri,
                                        PhotoSavePresenterImpl.this
                                ),
                                throwable -> onSaveError()
                        );
            } else {
                onSaveError();
            }
        }
        //editing already existing photo
        else {
            mPhoto.title = title;
            mPhoto.desc = desc;
            mInteractor.editPhoto(mPhoto, this);
        }

        mView.showProgressBar();
    }

    @Override
    public void onBackButtonClicked() {
        mView.finish();
    }

    @Override
    public void onSaveSuccess() {
        if (mView != null) {
            mView.finish(Config.PHOTO_SAVE_OK);
        }
    }

    @Override
    public void onSaveError() {
        if (mView != null) {
            mView.showMessage(PhotoSaveView.Message.ERROR);
            mView.finish(Config.PHOTO_SAVE_ERROR);
        }
    }
}
