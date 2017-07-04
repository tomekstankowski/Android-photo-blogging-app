package com.tomaszstankowski.trainingapplication;

import com.tomaszstankowski.trainingapplication.details.DetailsActivity;
import com.tomaszstankowski.trainingapplication.discover.DiscoverFragment;
import com.tomaszstankowski.trainingapplication.home.HomeFragment;
import com.tomaszstankowski.trainingapplication.login.LoginActivity;
import com.tomaszstankowski.trainingapplication.main.MainActivity;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsFragment;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;
import com.tomaszstankowski.trainingapplication.settings.SettingsFragment;
import com.tomaszstankowski.trainingapplication.user_details.MyUserDetailsFragment;
import com.tomaszstankowski.trainingapplication.user_details.UserDetailsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        PresenterModule.class,
        InteractorModule.class,
        UtilModule.class
})

public interface MainComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(DetailsActivity activity);

    void inject(PhotoDetailsFragment fragment);

    void inject(PhotoSaveActivity activity);

    void inject(HomeFragment fragment);

    void inject(DiscoverFragment fragment);

    void inject(UserDetailsFragment fragment);

    void inject(MyUserDetailsFragment fragment);

    void inject(SettingsFragment fragment);

}
