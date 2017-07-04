package com.tomaszstankowski.trainingapplication;

public class Config {

    /* Communication with LoginActivity*/
    public static final int RC_LOGIN = 111;
    public static final String LOGIN_MODE = "LOGIN_MODE";
    public static final int LOGIN_MODE_DEFAULT = 11;    //view started just after app launch
    public static final int LOGIN_MODE_LOGGED_OUT = 22;  //view started after logging out
    public static final int LOGIN_RESULT_OK = 200;

    /* Communication with PhotoSaveActivity*/
    public static final int RC_PHOTO_SAVE = 222;
    public static final int PHOTO_SAVE_OK = 200;
    public static final int PHOTO_SAVE_ERROR = 500;

    /* Communication with DetailsActivity*/
    public static final String DETAILS_MODE = "DETAILS_MODE";
    public static final int DETAILS_MODE_PHOTO = 11;
    public static final int DETAILS_MODE_USER = 22;

    /*Communication with system camera*/
    public static final int RC_CAMERA_CAPTURE = 444;
    public static final int CAMERA_RESULT_OK = 200;
    public static final int CAMERA_RESULT_ERROR = 500;

    /*Communication with AbstractUserDetailsFragment*/
    public static final String USER_DETAILS_MODE = "USER_DETAILS_MODE";
    public static final int USER_DETAILS_MODE_DEFAULT = 11;
    public static final int USER_DETAILS_MODE_CURRENT = 22;

    /*Communication with Firebase AuthUI*/
    public static final int RC_AUTH_UI = 999;

    public static final String ARG_PHOTO = "PHOTO";
    public static final String ARG_USER = "USER";
    public static final String ARG_TEMP_IMAGE_FILE = "TEMP_IMAGE_FILE";
}
