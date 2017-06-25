package com.tomaszstankowski.trainingapplication.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.photo_capture.PhotoCaptureFragment;
import com.tomaszstankowski.trainingapplication.settings.SettingsFragment;
import com.tomaszstankowski.trainingapplication.user_photos.UserPhotosFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {
    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.activity_main_bottom_navigation)
    BottomNavigationView mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((App) getApplication()).getMainComponent().inject(this);

        mNavigationBar.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.activity_main_menu_home:
                    fragment = new PhotoCaptureFragment();
                    break;
                case R.id.activity_main_menu_discover:
                    //// TODO: 4/14/2017
                    break;
                case R.id.activity_main_menu_my_profile:
                    fragment = new UserPhotosFragment();
                    break;
                case R.id.activity_main_menu_settings:
                    fragment = new SettingsFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_fragment_container, fragment)
                    .commit();
            return true;
        });

        mPresenter.onCreateView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStartView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showHomePage() {
        mNavigationBar.setSelectedItemId(R.id.activity_main_menu_home);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
