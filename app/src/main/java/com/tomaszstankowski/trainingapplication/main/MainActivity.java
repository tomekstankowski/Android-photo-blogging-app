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
import com.tomaszstankowski.trainingapplication.user_details.MyUserDetailsFragment;

import java.io.Serializable;
import java.util.Map;

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
            switch (item.getItemId()) {
                case R.id.activity_main_menu_home:
                    mPresenter.onNavigateRequest(Navigable.HOME);
                    break;
                case R.id.activity_main_menu_discover:
                    mPresenter.onNavigateRequest(Navigable.DISCOVER);
                    break;
                case R.id.activity_main_menu_my_profile:
                    mPresenter.onNavigateRequest(Navigable.MY_PROFILE);
                    break;
                case R.id.activity_main_menu_settings:
                    mPresenter.onNavigateRequest(Navigable.SETTINGS);
                    break;
            }
            return true;
        });
        if (savedInstanceState == null)
            navigate(Navigable.HOME, null);
        mPresenter.onCreateView(this);
    }

    @Override
    public void navigate(Navigable navigable, Map<String, Serializable> args) {
        Fragment fragment = null;
        switch (navigable) {
            case HOME:
                fragment = new HomeFragment();
                break;
            case DISCOVER:
                fragment = new DiscoverFragment();
                break;
            case MY_PROFILE:
                fragment = new MyUserDetailsFragment();
                break;
            case SETTINGS:
                fragment = new SettingsFragment();
                break;
        }
        if (args != null) {
            Bundle bundle = new Bundle();
            for (Map.Entry<String, Serializable> e : args.entrySet()) {
                bundle.putSerializable(e.getKey(), e.getValue());
            }
            fragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_fragment_container, fragment)
                .commit();
    }

    @Override
    public void startLoginView(int mode) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Config.LOGIN_MODE, mode);
        startActivityForResult(intent, Config.RC_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_LOGIN)
            mPresenter.onLoginViewResult(resultCode);
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
