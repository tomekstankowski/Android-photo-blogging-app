package com.tomaszstankowski.trainingapplication.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsFragment;
import com.tomaszstankowski.trainingapplication.user_details.UserDetailsFragment;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity implements DetailsView {

    public static void start(Context context, Map<String, Serializable> args) {
        Intent starter = new Intent(context, DetailsActivity.class);
        for (Map.Entry<String, Serializable> e : args.entrySet()) {
            starter.putExtra(e.getKey(), e.getValue());
        }
        context.startActivity(starter);
    }

    @Inject
    DetailsPresenter mPresenter;

    @BindView(R.id.activity_details_toolbar)
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ((App) getApplication()).getMainComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mPresenter.onCreateView(this);
        if (savedInstanceState == null) {
            mPresenter.onViewUpdateRequest();
        } else {
            setActionBarText();
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroyView();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void switchPage(Page page, Map<String, Serializable> args) {
        Fragment fragment = null;
        switch (page) {
            case PHOTO:
                fragment = new PhotoDetailsFragment();
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.photo);
                break;
            case USER:
                fragment = new UserDetailsFragment();
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.user);
                break;
        }
        Bundle bundle = new Bundle();
        for (Map.Entry<String, Serializable> e : args.entrySet()) {
            bundle.putSerializable(e.getKey(), e.getValue());
        }
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_details_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
            setActionBarText();
        } else {
            finish();
        }
    }

    @Override
    public void back() {
        onBackPressed();
    }

    @Override
    public Serializable getArg(String key) {
        return getIntent() == null ? null : getIntent().getSerializableExtra(key);
    }

    private void setActionBarText() {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.activity_details_container);
        if (fragment instanceof UserDetailsFragment) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(R.string.user);
        } else if (fragment instanceof PhotoDetailsFragment) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(R.string.photo);
        }
    }
}
