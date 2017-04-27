package com.tomaszstankowski.trainingapplication.photo_capture;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tomaszstankowski.trainingapplication.R;

/**
 * Fragment allowing to quickly capture photo and then save it.
 */

public class PhotoCaptureFragment extends Fragment implements PhotoCaptureView {
    private PhotoCapturePresenter mPresenter;
    private ProgressBar mProgressBar;
    private Button mCaptureButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(mPresenter == null)
            mPresenter = new PhotoCapturePresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_capture, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.fragment_photo_capture_progressbar);
        mPresenter.onViewUpdateRequest();
        setCaptureButton();
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
    }

    /**
     * PhotoCaptureView
     */
    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * PhotoCaptureView
     */
    @Override
    public void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * PhotoCaptureView
     */
    @Override
    public void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * PhotoCaptureView
     * Called when user successfully saved a photo or in onCreate() if last photo exists
     */
    @Override
    public void updateView(String title, Uri imageUri){
        TextView label = (TextView)getActivity().findViewById(R.id.fragment_photo_capture_textview_lastphoto);
        TextView titleTv = (TextView)getActivity().findViewById(R.id.fragment_photo_capture_textview_title);
        SimpleDraweeView image = (SimpleDraweeView)getActivity().findViewById(R.id.fragment_photo_capture_imageview_last_photo);
        label.setVisibility(View.VISIBLE);
        titleTv.setVisibility(View.VISIBLE);
        titleTv.setText(title);
        image.setVisibility(View.VISIBLE);
        int width = 50, height = 50;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(image.getController())
                .setImageRequest(request)
                .build();
        image.setController(controller);
        image.setImageURI(imageUri);
    }

    /**
     * PhotoCaptureView
     * Results from System Camera and PhotoSaveActivity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(getActivity(), requestCode, resultCode, data);
    }

    /**
     * Called in onActivityCreated()
     */
    private void setCaptureButton() {
        mCaptureButton = (Button) getActivity().findViewById(R.id.fragment_photo_capture_button);
        mCaptureButton.setOnClickListener(view -> mPresenter.onCaptureButtonClicked(getActivity()));
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
