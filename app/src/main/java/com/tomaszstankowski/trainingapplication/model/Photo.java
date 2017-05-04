package com.tomaszstankowski.trainingapplication.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class instances hold details of the photos taken by users of the app.
 */
@IgnoreExtraProperties
public class Photo implements Serializable{
    @Exclude
    public String key;
    public String userKey;
    public String title;
    public String desc;
    public Date date;

    /**
     * Called when initializing object from json
     */
    public Photo(){}

    /**
     * Called when initializing object for the first time
     */
    public Photo(@NonNull String title, @NonNull String desc, @NonNull String userKey) {
        this.title = title;
        this.userKey = userKey;
        this.desc = desc;
        date = new Date();
    }

    public String getDate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(date);
    }

    public void setDate(String date){
        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).parse(date);
        }catch (java.text.ParseException e){
            this.date = new Date();
        }
    }
}
