package com.tomaszstankowski.trainingapplication.home;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsActivity;
import com.tomaszstankowski.trainingapplication.photo_save.PhotoSaveActivity;
import com.tomaszstankowski.trainingapplication.util.CameraException;
import com.tomaszstankowski.trainingapplication.util.FileUtil;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Fragment allows to quickly capture photo and then save it.
 * Last photo taken by user is displayed here
 */
public class HomeFragment extends Fragment implements HomeView {
    @Inject
    HomePresenter mPresenter;

    @Inject
    FileUtil mFileUtil;

    @BindView(R.id.fragment_photo_capture_button)
    Button mCaptureButton;
    @BindView(R.id.fragment_photo_capture_imageview_last_photo)
    ImageView mImageView;
    @BindView(R.id.fragment_photo_capture_textview_lastphoto)
    TextView mLabelTv;

    @OnClick(R.id.fragment_photo_capture_button)
    public void onCaptureButtonClicked() {
        mPresenter.onCaptureButtonClicked();
    }

    @OnClick(R.id.fragment_photo_capture_imageview_last_photo)
    public void onImageClicked() {
        mPresenter.onImageClicked();
    }

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getMainComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onCreateView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        animateCaptureButton();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDestroyView();
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void startSystemCamera(File targetFile) throws CameraException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (targetFile != null
                && takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            Uri targetUri = mFileUtil.getUriFromFile(targetFile);
            if (targetUri != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
                startActivityForResult(takePictureIntent, Config.RC_CAMERA_CAPTURE);
                return;
            }
        }
        throw new CameraException();
    }

    @Override
    public void startPhotoDetailsView() {
        Intent intent = new Intent(getActivity(), PhotoDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void startPhotoSaveView() {
        Intent intent = new Intent(getActivity(), PhotoSaveActivity.class);
        startActivityForResult(intent, Config.RC_PHOTO_SAVE);
    }

    @Override
    public void showMessage(Message message) {
        switch (message) {
            case CAMERA_ERROR:
                Toast.makeText(getActivity(), R.string.camera_error, Toast.LENGTH_LONG).show();
                break;
            case LOAD_ERROR:
                Toast.makeText(getActivity(), R.string.load_error, Toast.LENGTH_LONG).show();
                break;
        }
    }


    @Override
    public void updateView(StorageReference image) {
        mLabelTv.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.VISIBLE);
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(image)
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .into(mImageView);
    }

    @Override
    public void clearView() {
        mLabelTv.setVisibility(View.GONE);
        mImageView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_CAMERA_CAPTURE) {
            //presenter does not know android result codes - we need to translate it
            if (resultCode == RESULT_OK)
                mPresenter.onSystemCameraResult(Config.CAMERA_RESULT_OK);
            else
                mPresenter.onSystemCameraResult(Config.CAMERA_RESULT_ERROR);
        }
        if (requestCode == Config.RC_PHOTO_SAVE) {
            mPresenter.onPhotoSaveViewResult(resultCode);
        }
    }

    private void animateCaptureButton() {
        Animator slideDown = AnimatorInflater.loadAnimator(getActivity(), R.animator.slide_from_top);
        Animator fadeIn = AnimatorInflater.loadAnimator(getActivity(), R.animator.fade_in);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(slideDown, fadeIn);
        set.setTarget(mCaptureButton);
        set.start();
    }
}
