package com.tomaszstankowski.trainingapplication.view;

import android.app.Activity;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.model.DAO;
import com.tomaszstankowski.trainingapplication.model.Model;
import com.tomaszstankowski.trainingapplication.presenter.Presenter;

/**
 * Entry point for initializing other Views and Model
 */

public class MainActivity extends AppCompatActivity implements View{
    private final static String PHOTO_CAPTURE_FRAGMENT_TAG = "PHOTO_CAPTURE_FRAGMENT";
    private PhotoCaptureFragment mPhotoCaptureFragment;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new DAO();
        mPhotoCaptureFragment = (PhotoCaptureFragment) getSupportFragmentManager()
                .findFragmentByTag(PHOTO_CAPTURE_FRAGMENT_TAG);
        if(mPhotoCaptureFragment == null) {
            mPhotoCaptureFragment = new PhotoCaptureFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_main_fragment_container, mPhotoCaptureFragment, PHOTO_CAPTURE_FRAGMENT_TAG)
                    .commit();
            mPhotoCaptureFragment.getPresenter().setModel(model);
        }
        setBottomNavigation();
    }

    @Override
    public Presenter getPresenter(){
        return null;
    }

    @Override
    public Activity getContext(){
        return this;
    }

    @Override
    public void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    /**
     * Called in onCreate()
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
                        mPhotoCaptureFragment.getPresenter().setModel(model);
                    }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.activity_main_fragment_container, mPhotoCaptureFragment, PHOTO_CAPTURE_FRAGMENT_TAG)
                            .commit();
                    break;
                case R.id.activity_main_menu_discover:
                    //// TODO: 4/14/2017
                    break;
                case R.id.activity_main_menu_me:
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
