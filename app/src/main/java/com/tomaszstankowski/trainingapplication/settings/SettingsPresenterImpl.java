package com.tomaszstankowski.trainingapplication.settings;


import android.content.Intent;

import com.firebase.ui.auth.AuthUI;
import com.tomaszstankowski.trainingapplication.login.LoginActivity;

import javax.inject.Inject;

public class SettingsPresenterImpl implements SettingsPresenter {
    private SettingsView mView;

    @Inject
    public SettingsPresenterImpl() {
    }

    @Override
    public void onCreateView(SettingsView view) {
        mView = view;
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }

    @Override
    public void onLogOutButtonClicked() {
        AuthUI.getInstance()
                .signOut(mView.getActivity())
                .addOnCompleteListener(task -> {
                    if (mView != null) {
                        Intent intent = new Intent(mView.getActivity(), LoginActivity.class);
                        intent.putExtra(LoginActivity.REQUEST_CODE, LoginActivity.REQUEST_CODE_LOGGED_OUT);
                        mView.getActivity().startActivityForResult(
                                intent,
                                LoginActivity.REQUEST_CODE_LOGGED_OUT);
                    }
                });
    }
}
