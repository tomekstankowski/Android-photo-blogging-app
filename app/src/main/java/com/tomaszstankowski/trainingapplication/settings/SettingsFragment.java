package com.tomaszstankowski.trainingapplication.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.R;
import com.tomaszstankowski.trainingapplication.login.LoginActivity;

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

    @BindView(R.id.fragment_settings_button_save)
    Button mSaveButtn;

    @OnClick(R.id.fragment_settings_button_save)
    public void onSaveButtonClicked() {
        mPresenter.onSaveButtonClicked(mUsernameEditTxt.getText().toString());
    }

    @BindView(R.id.fragment_settings_edittext_username)
    EditText mUsernameEditTxt;

    @BindView(R.id.fragment_settings_progressbar)
    ProgressBar mProgressbar;

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
    public void startLogOutUI() {
        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(task -> mPresenter.onLogOutCompleted());
    }

    @Override
    public void startLoginView(int mode) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(Config.LOGIN_VIEW_MODE, mode);
        getActivity().startActivityForResult(
                intent,
                Config.RC_LOGIN);
    }

    @Override
    public void update(String username) {
        mUsernameEditTxt.setText(username);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDestroyView();
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void showMessage(Message message) {
        switch (message) {
            case SUCCESS:
                Toast.makeText(getActivity(), R.string.saved_changes, Toast.LENGTH_SHORT).show();
                break;
            case UNCHANGED:
                Toast.makeText(getActivity(), R.string.no_changes, Toast.LENGTH_SHORT).show();
                break;
            case ERROR:
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showProgressbar() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mProgressbar.setVisibility(View.GONE);
    }
}
