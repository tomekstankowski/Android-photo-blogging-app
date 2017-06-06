package com.tomaszstankowski.trainingapplication.photo_save;

import android.net.Uri;

import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.StorageAccessor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhotoSaveInteractorImpl implements PhotoSaveInteractor {
    @Inject
    DataBaseAccessor mDataAccessor;

    @Inject
    StorageAccessor mStorageAccessor;

    @Inject
    PhotoSaveInteractorImpl() {
    }

    @Override
    public void editPhoto(Photo photo, OnPhotoSaveListener listener) {
        mDataAccessor.editPhoto(photo)
                .addOnSuccessListener(aVoid -> listener.onSaveSuccess())
                .addOnFailureListener(r -> listener.onSaveError());
    }

    @Override
    public void savePhoto(Photo photo, Uri imageUri, OnPhotoSaveListener listener) {
        mDataAccessor.savePhoto(photo)
                .addOnSuccessListener(aVoid -> mStorageAccessor.saveImage(photo, imageUri)
                        .addOnSuccessListener(taskSnapshot -> listener.onSaveSuccess())
                        .addOnFailureListener(e -> listener.onSaveError()))
                .addOnFailureListener(r -> listener.onSaveError());
    }

}
