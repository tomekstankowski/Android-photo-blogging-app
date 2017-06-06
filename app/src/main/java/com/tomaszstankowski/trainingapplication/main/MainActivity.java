package com.tomaszstankowski.trainingapplication.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
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
    private final static String PHOTO_CAPTURE_FRAGMENT_TAG = "PHOTO_CAPTURE_FRAGMENT";
    private final static String USER_PHOTOS_FRAGMENT_TAG = "USER_PHOTOS_FRAGMENT";

    private PhotoCaptureFragment mPhotoCaptureFragment;
    private UserPhotosFragment mUserPhotosFragment;
    private MainPresenter mPresenter;

    @BindView(R.id.activity_main_bottom_navigation)
    BottomNavigationView mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(mPresenter == null)
            mPresenter = new MainPresenterImpl(this);
        //fragment that shows up after launch
        mPhotoCaptureFragment = (PhotoCaptureFragment) getSupportFragmentManager()
                .findFragmentByTag(PHOTO_CAPTURE_FRAGMENT_TAG);
        if(mPhotoCaptureFragment == null) {
            mPhotoCaptureFragment = new PhotoCaptureFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_main_fragment_container, mPhotoCaptureFragment, PHOTO_CAPTURE_FRAGMENT_TAG)
                    .commit();
        }
        setBottomNavigation();
    }

    @Override
    public void onDestroy(){
        mPresenter.onDestroy();
        super.onDestroy();
    }

    /**
     * Called in onCreate().
     */
    private void setBottomNavigation(){
        mNavigationBar.setOnNavigationItemSelectedListener(item -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (item.getItemId()){
                case R.id.activity_main_menu_home:
                    mPhotoCaptureFragment = (PhotoCaptureFragment)getSupportFragmentManager()
                            .findFragmentByTag(PHOTO_CAPTURE_FRAGMENT_TAG);
                    if (mPhotoCaptureFragment == null)
                        mPhotoCaptureFragment = new PhotoCaptureFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.activity_main_fragment_container, mPhotoCaptureFragment, PHOTO_CAPTURE_FRAGMENT_TAG)
                            .commit();
                    break;
                case R.id.activity_main_menu_discover:
                    //// TODO: 4/14/2017
                    break;
                case R.id.activity_main_menu_my_profile:
                    mUserPhotosFragment = (UserPhotosFragment) getSupportFragmentManager()
                            .findFragmentByTag(USER_PHOTOS_FRAGMENT_TAG);
                    if (mUserPhotosFragment == null)
                        mUserPhotosFragment = new UserPhotosFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.activity_main_fragment_container, mUserPhotosFragment, USER_PHOTOS_FRAGMENT_TAG)
                            .commit();
                    break;
                case R.id.activity_main_menu_settings:
                    //// TODO: 4/14/2017
                    break;
            }
            return true;
        });
    }

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
