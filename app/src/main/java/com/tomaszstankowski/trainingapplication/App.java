package com.tomaszstankowski.trainingapplication;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {
    private MainComponent mMainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mMainComponent = DaggerMainComponent.builder()
                .appModule(new AppModule(this))
                .presenterModule(new PresenterModule())
                .interactorModule(new InteractorModule())
                .utilModule(new UtilModule())
                .loginModule(new LoginModule())
                .build();
    }

    public MainComponent getMainComponent() {
        return mMainComponent;
    }
}
