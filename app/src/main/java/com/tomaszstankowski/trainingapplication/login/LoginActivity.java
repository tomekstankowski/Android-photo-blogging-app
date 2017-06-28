package com.tomaszstankowski.trainingapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.Config;
import com.tomaszstankowski.trainingapplication.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tomaszstankowski.trainingapplication.Config.RC_AUTH_UI;

/**
 * Business logic is done in Firebase AuthUI, which is started from here.
 * This fragment is responsible for displaying view according to whether user wants to sign in for
 * the first time or just logged out and also displaying error message.
 * It's holding unsigned user from the application.
 */

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Inject
    LoginPresenter mPresenter;

    @BindView(R.id.activity_login_textview_uuups)
    TextView mUuupsTv;
    @BindView(R.id.activity_login_textview_message)
    TextView mMessageTv;
    @BindView(R.id.activity_login_button_log_in)
    Button mLogInButton;
    @BindView(R.id.activity_login_progressbar)
    ProgressBar mProgressbar;

    @OnClick(R.id.activity_login_button_log_in)
    public void onLogInButtonClicked() {
        mPresenter.onLogInButtonClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((App) getApplication()).getMainComponent().inject(this);
        Intent intent = getIntent();
        int mode = Config.LOGIN_VIEW_MODE_DEFAULT;
        if (intent != null)
            mode = intent.getIntExtra(Config.LOGIN_VIEW_MODE, mode);
        mPresenter.onCreateView(this, mode);
    }

    @Override
    public void startAuthUI(AuthUI.SignInIntentBuilder builder) {
        builder.setTheme(R.style.AppTheme);
        startActivityForResult(builder.build(), RC_AUTH_UI);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_AUTH_UI) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            mPresenter.onAuthUIResult(resultCode, response);
        }
    }

    @Override
    public void onBackPressed() {
        //not allowed
    }

    @Override
    public void finish(int resultCode) {
        setResult(resultCode);
        finish();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroyView();
        super.onDestroy();
    }

    @Override
    public void showLoggedOutView() {
        mMessageTv.setVisibility(View.VISIBLE);
        mMessageTv.setText(R.string.logged_out);
        mLogInButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressbar() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showRetryView(Message message) {
        mUuupsTv.setVisibility(View.VISIBLE);
        mMessageTv.setVisibility(View.VISIBLE);
        switch (message) {
            case NO_NETWORK:
                mMessageTv.setText(R.string.no_internet_connection);
                break;
            case ERROR:
                mMessageTv.setText(R.string.error);
                break;
            case SIGN_IN_REQUIRED:
                mMessageTv.setText(R.string.sign_in_required);
                break;
        }
        mLogInButton.setVisibility(View.VISIBLE);
    }
}
