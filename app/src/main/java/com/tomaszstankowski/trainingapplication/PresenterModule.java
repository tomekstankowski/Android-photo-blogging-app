package com.tomaszstankowski.trainingapplication;

import com.tomaszstankowski.trainingapplication.photo_capture.PhotoCapturePresenter;
import com.tomaszstankowski.trainingapplication.photo_capture.PhotoCapturePresenterImpl;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsPresenter;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsPresenterImpl;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSavePresenter;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSavePresenterImpl;
import com.tomaszstankowski.trainingapplication.user_photos.UserPhotosPresenter;
import com.tomaszstankowski.trainingapplication.user_photos.UserPhotosPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    PhotoCapturePresenter providesPhotoCapturePresenter(PhotoCapturePresenterImpl impl) {
        return impl;
    }

    @Provides
    @Singleton
    PhotoDetailsPresenter providesPhotoDetailsPresenter(PhotoDetailsPresenterImpl impl) {
        return impl;
    }

    @Provides
    @Singleton
    PhotoSavePresenter providesPhotoSavePresenter(PhotoSavePresenterImpl impl) {
        return impl;
    }

    @Provides
    @Singleton
    UserPhotosPresenter providesUserPhotosPresenter(UserPhotosPresenterImpl impl) {
        return impl;
    }
}
