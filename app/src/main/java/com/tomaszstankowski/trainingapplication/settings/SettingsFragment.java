package com.tomaszstankowski.trainingapplication.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SettingsFragment extends Fragment implements SettingsView {

    @Inject
    SettingsPresenter mPresenter;

    private Unbinder mUnbinder;

    @BindView(R.id.fragment_settings_button_log_out)
    Button mLogOutBttn;

    @OnClick(R.id.fragment_settings_button_log_out)
    public void onLogOutButtonClicked() {
        mPresenter.onLogOutButtonClicked();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getMainComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onCreateView(this);
    }


    @Override
    public void onDestroyView() {
        mPresenter.onDestroyView();
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
