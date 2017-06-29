package com.tomaszstankowski.trainingapplication;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {
    private MainComponent mMainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mMainComponent = DaggerMainComponent.builder()
                .appModule(new AppModule(this))
                .presenterModule(new PresenterModule())
                .interactorModule(new InteractorModule())
                .utilModule(new UtilModule())
                .build();
    }

    public MainComponent getMainComponent() {
        return mMainComponent;
    }
}
