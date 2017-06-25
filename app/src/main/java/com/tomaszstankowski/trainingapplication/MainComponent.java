package com.tomaszstankowski.trainingapplication;

import com.tomaszstankowski.trainingapplication.login.LoginActivity;
import com.tomaszstankowski.trainingapplication.main.MainActivity;
import com.tomaszstankowski.trainingapplication.photo_capture.PhotoCaptureFragment;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsActivity;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;
import com.tomaszstankowski.trainingapplication.settings.SettingsFragment;
import com.tomaszstankowski.trainingapplication.user_photos.UserPhotosFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        PresenterModule.class,
        InteractorModule.class,
        UtilModule.class,
        LoginModule.class,
})

public interface MainComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(PhotoCaptureFragment fragment);

    void inject(UserPhotosFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(PhotoDetailsActivity activity);

    void inject(PhotoSaveActivity activity);
}
