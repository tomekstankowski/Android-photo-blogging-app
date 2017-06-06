package com.tomaszstankowski.trainingapplication;

import com.tomaszstankowski.trainingapplication.photo_capture.PhotoCaptureFragment;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsActivity;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;
import com.tomaszstankowski.trainingapplication.user_photos.UserPhotosFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        ModelModule.class,
        PresenterModule.class,
        InteractorModule.class,
        UtilModule.class})
public interface MainComponent {
    void inject(PhotoCaptureFragment fragment);

    void inject(UserPhotosFragment fragment);

    void inject(PhotoDetailsActivity activity);

    void inject(PhotoSaveActivity activity);
}
