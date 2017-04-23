package com.tomaszstankowski.trainingapplication.model;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Class representing photos taken by users of the app.
 */

public class Photo implements Identifiable, Serializable{
    public String title;
    public String author;
    public String desc;
    public Date date;
    public Uri uri;
    public UUID id;

    /**
     * Called when initializing object from json
     */
    public Photo(){}

    /**
     * Called when initializing object for the first time
     */
    public Photo(@NonNull String title, @NonNull String author, @NonNull String desc, @NonNull Uri uri){
        this.title =title;
        this.author =author;
        this.desc =desc;
        this.uri =uri;
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
        this.author =author;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
        this.desc =desc;
    }

    public Date getDate(){
        return date;
    }

    public UUID getId(){
        return id;
    }

    @Override
    public String getKey(){
        return id.toString();
    }

    @Override
    public String toString(){
        return title + " - " + author;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Photo){
            Photo p = (Photo)o;
            return p.getId().equals(id);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
