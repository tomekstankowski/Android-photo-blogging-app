package com.tomaszstankowski.trainingapplication;

import android.app.Application;

import com.tomaszstankowski.trainingapplication.util.ImageManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilModule {

    @Provides
    @Singleton
    ImageManager providesImageManager(Application application) {
        return new ImageManager(application);
    }
}
