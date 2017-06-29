package com.tomaszstankowski.trainingapplication.photo_save;

import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.Photo;
import com.tomaszstankowski.trainingapplication.model.StorageAccessor;

import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhotoSaveInteractorImpl implements PhotoSaveInteractor {
    private DataBaseAccessor mDataAccessor;
    private StorageAccessor mStorageAccessor;

    @Inject
    PhotoSaveInteractorImpl(DataBaseAccessor dataBaseAccessor, StorageAccessor storageAccessor) {
        mDataAccessor = dataBaseAccessor;
        mStorageAccessor = storageAccessor;
    }

    @Override
    public void editPhoto(Photo photo, OnPhotoSaveListener listener) {
        mDataAccessor.editPhoto(photo)
                .addOnSuccessListener(aVoid -> listener.onSaveSuccess())
                .addOnFailureListener(r -> listener.onSaveError());
    }

    @Override
    public void savePhoto(Photo photo, InputStream imageInputStream, OnPhotoSaveListener listener) {
        mDataAccessor.savePhoto(photo)
                .addOnSuccessListener(aVoid -> mStorageAccessor.saveImage(photo.key, imageInputStream)
                        .addOnSuccessListener(taskSnapshot -> listener.onSaveSuccess())
                        .addOnFailureListener(e -> listener.onSaveError()))
                .addOnFailureListener(r -> listener.onSaveError());
    }

    @Override
    public StorageReference getImage(Photo photo) {
        return mStorageAccessor.getImage(photo.key);
    }

}
