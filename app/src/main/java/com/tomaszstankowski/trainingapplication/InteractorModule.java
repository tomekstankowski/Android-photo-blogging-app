package com.tomaszstankowski.trainingapplication;

import com.tomaszstankowski.trainingapplication.photo_capture.PhotoCaptureInteractor;
import com.tomaszstankowski.trainingapplication.photo_capture.PhotoCaptureInteractorImpl;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsInteractor;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsInteractorImpl;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveInteractor;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveInteractorImpl;
import com.tomaszstankowski.trainingapplication.user_photos.UserPhotosInteractor;
import com.tomaszstankowski.trainingapplication.user_photos.UserPhotosInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Singleton
    @Provides
    PhotoCaptureInteractor providesPhotoCaptureInteractor(PhotoCaptureInteractorImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    PhotoDetailsInteractor providesPhotoDetailsInteractor(PhotoDetailsInteractorImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    PhotoSaveInteractor providesPhotoSaveInteractor(PhotoSaveInteractorImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    UserPhotosInteractor providesUserPhotosInteractor(UserPhotosInteractorImpl impl) {
        return impl;
    }
}
