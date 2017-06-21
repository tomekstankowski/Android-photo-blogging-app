package com.tomaszstankowski.trainingapplication.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.photo_capture.PhotoCaptureFragment;
import com.tomaszstankowski.trainingapplication.user_photos.UserPhotosFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Entry point for initializing other Views(fragments)
 */

public class MainActivity extends AppCompatActivity implements MainView {
    private MainPresenter mPresenter;

    @BindView(R.id.activity_main_bottom_navigation)
    BottomNavigationView mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (mPresenter == null)
            mPresenter = new MainPresenterImpl(this);
        //make sure we don't create another fragment on configuration change
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_fragment_container, new PhotoCaptureFragment())
                    .commit();
        }
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
                    //// TODO: 4/14/2017
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_fragment_container, fragment)
                    .commit();
            return true;
        });
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
