package com.tomaszstankowski.trainingapplication;

import com.tomaszstankowski.trainingapplication.model.DataBaseAccessor;
import com.tomaszstankowski.trainingapplication.model.StorageAccessor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    @Singleton
    DataBaseAccessor providesDataBaseAccessor() {
        return new DataBaseAccessor();
    }

    @Provides
    @Singleton
    StorageAccessor providesStorageAccessor() {
        return new StorageAccessor();
    }
}
