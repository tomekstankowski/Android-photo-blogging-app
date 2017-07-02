package com.tomaszstankowski.trainingapplication.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.discover.DiscoverFragment;
import com.tomaszstankowski.trainingapplication.home.HomeFragment;
import com.tomaszstankowski.trainingapplication.login.LoginActivity;
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
                    fragment = new HomeFragment();
                    break;
                case R.id.activity_main_menu_discover:
                    fragment = new DiscoverFragment();
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
        if (savedInstanceState == null)
            showHomePage();
        mPresenter.onCreateView(this);
    }

    @Override
    public void startLoginView(int mode) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Config.LOGIN_VIEW_MODE, mode);
        startActivityForResult(intent, Config.RC_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_LOGIN)
            mPresenter.onLoginViewResult(resultCode);
    }

    @Override
    public void showHomePage() {
        mNavigationBar.setSelectedItemId(R.id.activity_main_menu_home);
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
