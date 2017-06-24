package com.tomaszstankowski.trainingapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tomaszstankowski.trainingapplication.App;
import com.tomaszstankowski.trainingapplication.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Actually signing up is not performed in this fragment.
 * It's only responsible for displaying error message and 'retry' button.
 * Holding unsigned user from application.
 */

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Inject
    LoginPresenter mPresenter;

    @BindView(R.id.activity_login_textview_uuups)
    TextView mUuupsTv;
    @BindView(R.id.activity_login_textview_message)
    TextView mMessageTv;
    @BindView(R.id.activity_login_button_retry)
    Button mRetryButton;

    @OnClick(R.id.activity_login_button_retry)
    public void onRetryButtonClicked() {
        mPresenter.onRetryButtonClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((App) getApplication()).getMainComponent().inject(this);
        mPresenter.onCreateView(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        //no back allowed
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroyView();
        super.onDestroy();
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
        mRetryButton.setVisibility(View.VISIBLE);
    }

    @Override
    public int getThemeId() {
        return R.style.AppTheme;
    }
}
