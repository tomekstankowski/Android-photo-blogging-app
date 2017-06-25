package com.tomaszstankowski.trainingapplication.main;

import android.content.Context;
import android.content.Intent;

public interface MainView {

    void startActivityForResult(Intent intent, int requestCode);

    Context getContext();

    void showHomePage();
}
