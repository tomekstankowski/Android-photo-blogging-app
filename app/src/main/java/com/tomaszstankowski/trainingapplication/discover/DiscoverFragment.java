package com.tomaszstankowski.trainingapplication.discover;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.storage.StorageReference;
import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.photo_details.PhotoDetailsActivity;
import com.tomaszstankowski.trainingapplication.ui.GalleryViewAdapter;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DiscoverFragment extends Fragment implements DiscoverView,
        GalleryViewAdapter.OnItemClickListener {

    @Inject
    DiscoverPresenter mPresenter;
    private Unbinder mUnbinder;
    private GalleryViewAdapter mAdapter;

    @BindView(R.id.fragment_discover_recyclerview)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getMainComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discover, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        mAdapter = new GalleryViewAdapter(getActivity());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.onCreateView(this);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDestroyView();
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void startPhotoDetailsView(Map<String, Serializable> args) {
        PhotoDetailsActivity.start(getActivity(), args);
    }

    @Override
    public void addPhoto(StorageReference image) {
        mAdapter.addItem(image);
    }

    @Override
    public void removePhoto(int position) {
        mAdapter.removeItem(position);
    }

    @Override
    public void onItemClick(View view, int position) {
        mPresenter.onPhotoClicked(position);
    }
}
