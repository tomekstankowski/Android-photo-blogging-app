package com.tomaszstankowski.trainingapplication.photo_capture;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment allowing to quickly capture photo and then save it.
 */

public class PhotoCaptureFragment extends Fragment implements PhotoCaptureView {
    @Inject
    PhotoCapturePresenter mPresenter;

    @BindView(R.id.fragment_photo_capture_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.fragment_photo_capture_button)
    Button mCaptureButton;
    @BindView(R.id.fragment_photo_capture_imageview_last_photo)
    SimpleDraweeView mImage;
    @BindView(R.id.fragment_photo_capture_textview_lastphoto)
    TextView mLabel;

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
        View v = inflater.inflate(R.layout.fragment_photo_capture, container, false);
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
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Called when user successfully saved a photo or in onCreate() if last photo exists
     */
    @Override
    public void updateView(Uri imageUri) {
        mLabel.setVisibility(View.VISIBLE);
        mImage.setVisibility(View.VISIBLE);
        int width = 50, height = 50;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(mImage.getController())
                .setImageRequest(request)
                .build();
        mImage.setController(controller);
        mImage.setImageURI(imageUri);
    }

    /**
     * Results from System Camera and PhotoSaveActivity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Called in onResume()
     */
    private void animateCaptureButton() {
        Animator slideDown = AnimatorInflater.loadAnimator(getActivity(), R.animator.slide_from_top);
        Animator fadeIn = AnimatorInflater.loadAnimator(getActivity(), R.animator.fade_in);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(slideDown, fadeIn);
        set.setTarget(mCaptureButton);
        set.start();
    }
}
