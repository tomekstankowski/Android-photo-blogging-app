package com.tomaszstankowski.trainingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Class instances hold details of the photos taken by users of the app.
 */
@IgnoreExtraProperties
public class Photo implements Serializable, Parcelable {
    @Exclude()
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.userKey);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    protected Photo(Parcel in) {
        this.key = in.readString();
        this.userKey = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
