package com.tomaszstankowski.trainingapplication;

public class Config {

    /* Communication with LoginActivity*/
    public static final int RC_LOGIN = 111;
    public static final String LOGIN_VIEW_MODE = "LOGIN_VIEW_MODE";
    public static final int LOGIN_VIEW_MODE_DEFAULT = 11;    //view started just after app launch
    public static final int LOGIN_VIEW_MODE_LOGGED_OUT = 22;  //view started after logging out
    public static final int LOGIN_RESULT_OK = 200;

    /* Communication with PhotoSaveActivity*/
    public static final int RC_PHOTO_SAVE = 222;
    public static final int PHOTO_SAVE_OK = 200;
    public static final int PHOTO_SAVE_ERROR = 500;

    /* Communication with PhotoDetailsActivity*/
    public static final int RC_PHOTO_DETAILS = 333;

    /*Communication with system camera*/
    public static final int RC_CAMERA_CAPTURE = 444;
    public static final int CAMERA_RESULT_OK = 200;
    public static final int CAMERA_RESULT_ERROR = 500;

    /*Communication with Firebase AuthUI*/
    public static final int RC_AUTH_UI = 999;

    public static final String ARG_PHOTO = "PHOTO";
    public static final String ARG_TEMP_IMAGE_FILE = "TEMP_IMAGE_FILE";
}
