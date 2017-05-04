package com.tomaszstankowski.trainingapplication.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.photo_capture.PhotoCaptureFragment;

/**
 * Entry point for initializing other Views(fragments)
 */

public class MainActivity extends AppCompatActivity implements MainView {
    private final static String PHOTO_CAPTURE_FRAGMENT_TAG = "PHOTO_CAPTURE_FRAGMENT";
    private PhotoCaptureFragment mPhotoCaptureFragment;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        BottomNavigationView bar = (BottomNavigationView)findViewById(R.id.activity_main_bottom_navigation);
        bar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.activity_main_menu_home:
                    mPhotoCaptureFragment = (PhotoCaptureFragment)getSupportFragmentManager()
                            .findFragmentByTag(PHOTO_CAPTURE_FRAGMENT_TAG);
                    if(mPhotoCaptureFragment == null) {
                        mPhotoCaptureFragment = new PhotoCaptureFragment();
                    }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.activity_main_fragment_container, mPhotoCaptureFragment, PHOTO_CAPTURE_FRAGMENT_TAG)
                            .commit();
                    break;
                case R.id.activity_main_menu_discover:
                    //// TODO: 4/14/2017
                    break;
                case R.id.activity_main_menu_my_profile:
                    //// TODO: 4/14/2017
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
