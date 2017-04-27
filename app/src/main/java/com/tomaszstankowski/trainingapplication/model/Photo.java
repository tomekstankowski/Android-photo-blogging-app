package com.tomaszstankowski.trainingapplication.model;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Class representing photos taken by users of the app.
 */

public class Photo implements Serializable{
    private String title;
    private String author;
    private String desc;
    private Date date;
    private Uri uri;
    private UUID id;

    /**
     * Called when initializing object from json
     */
    public Photo(){}

    /**
     * Called when initializing object for the first time
     */
    public Photo(@NonNull String title, @NonNull String author, @NonNull String desc, @NonNull Uri uri){
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.uri = uri;
        date = new Date();
        id = UUID.randomUUID();
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getKey(){
        return id.toString();
    }

    public void setKey(String key){
        id = UUID.fromString(key);
    }

    public String getUri(){
        return uri.toString();
    }

    public void setUri(String uri){
        this.uri = Uri.parse(uri);
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
    public boolean equals(Object o){
        if(o instanceof Photo){
            Photo p = (Photo)o;
            return p.id.equals(id);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
