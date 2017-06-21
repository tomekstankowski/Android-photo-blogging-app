package com.tomaszstankowski.trainingapplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    @Singleton
    @Named("userKey")
    String providesUserKey() {
        return "admin";
    }
}
