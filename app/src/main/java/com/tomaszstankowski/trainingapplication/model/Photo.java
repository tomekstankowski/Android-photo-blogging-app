package com.tomaszstankowski.trainingapplication.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Class instances hold details of the photos taken by users of the app.
 */
@IgnoreExtraProperties
public class Photo implements Serializable {

    @Exclude()
    public String key;
    @PropertyName("user")
    public String userKey;
    public String title;
    public String desc;
    public Date date;

    public Photo(){}

    public Photo(String title, String desc, String userKey) {
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
        } catch (java.text.ParseException e) {
            this.date = new Date();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(key, photo.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
