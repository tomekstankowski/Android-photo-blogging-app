package com.tomaszstankowski.trainingapplication;

import android.app.Application;

import com.tomaszstankowski.trainingapplication.util.FileUtil;
import com.tomaszstankowski.trainingapplication.util.ImageManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class UtilModule {

    @Provides
    @Singleton
    ImageManager providesImageManager(Application application) {
        return new ImageManager(application);
    }

    @Provides
    @Singleton
    FileUtil providesFileUtil(Application application) {
        return new FileUtil(application);
    }
}
