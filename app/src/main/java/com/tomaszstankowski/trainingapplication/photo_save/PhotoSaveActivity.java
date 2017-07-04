package com.tomaszstankowski.trainingapplication.photo_save;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.R;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity starts after user captures photo or edits already existing one.
 * He can set title and desc and save photo.
 */

public class PhotoSaveActivity extends AppCompatActivity implements PhotoSaveView {

    public static void start(Fragment fragment, Map<String, Serializable> extras) {
        Intent starter = new Intent(fragment.getContext(), PhotoSaveActivity.class);
        for (Map.Entry<String, Serializable> e : extras.entrySet()) {
            starter.putExtra(e.getKey(), e.getValue());
        }
        fragment.startActivity(starter);
    }

    public static void startForResult(Fragment fragment, int requestCode,
                                      Map<String, Serializable> extras) {
        Intent starter = new Intent(fragment.getContext(), PhotoSaveActivity.class);
        for (Map.Entry<String, Serializable> e : extras.entrySet()) {
            starter.putExtra(e.getKey(), e.getValue());
        }
        fragment.startActivityForResult(starter, requestCode);
    }

    @Inject
    PhotoSavePresenter mPresenter;

    @BindView(R.id.activity_photo_save_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.activity_photo_save_edittext_title)
    EditText mTitle;
    @BindView(R.id.activity_photo_save_edittext_desc)
    EditText mDesc;
    @BindView(R.id.activity_photo_save_imageview_photo)
    ImageView mImageView;
    @BindView(R.id.activity_photo_save_button_back)
    Button mBackButton;
    @BindView(R.id.activity_photo_save_button_save)
    Button mSaveButton;

    @OnClick(R.id.activity_photo_save_button_back)
    public void onBackButtonClicked() {
        mPresenter.onBackButtonClicked();
    }

    @OnClick(R.id.activity_photo_save_button_save)
    public void onSaveButtonClicked() {
        String title = mTitle.getText().toString();
        String desc = mDesc.getText().toString();
        mPresenter.onSaveButtonClicked(title, desc);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_save);
        ButterKnife.bind(this);
        ((App) getApplication()).getMainComponent().inject(this);
        mPresenter.onCreateView(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroyView();
        super.onDestroy();
    }

    @Override
    public Serializable getArg(String key) {
        Intent intent = getIntent();
        return intent != null ? intent.getSerializableExtra(key) : null;
    }

    @Override
    public void updateEditable(String title, String desc) {
        mTitle.setText(title);
        mDesc.setText(desc);
    }

    @Override
    public void showImage(File imageFile) {
        Glide.with(this)
                .fromFile()
                .load(imageFile)
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .into(mImageView);
    }

    @Override
    public void showImage(StorageReference image) {
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(image)
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .into(mImageView);
    }

    @Override
    public void showMessage(Message message) {
        switch (message) {
            case ERROR:
                Toast.makeText(this, R.string.save_error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void finish(int resultCode) {
        setResult(resultCode);
        finish();
    }
}
