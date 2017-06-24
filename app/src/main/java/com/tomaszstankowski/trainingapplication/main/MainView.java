package com.tomaszstankowski.trainingapplication.main;

import android.content.Context;
import android.content.Intent;

public interface MainView {

    void startActivity(Intent intent);

    Context getContext();
}
