package com.tomaszstankowski.trainingapplication.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Tomek on 4/18/2017.
 */

public class DAO implements Model{
    private PhotoAccessor photoAccessor;

    public DAO(){
        photoAccessor = new PhotoAccessor();
    }

    @Override
    public Task<Void> savePhoto(Photo photo){
        return photoAccessor.savePhoto(photo);
    }

    @Override
    public Task<Void> setLastPhoto(Photo photo){
        return photoAccessor.setLastPhoto(photo);
    }

    @Override
    public void getPhoto(String key, ValueEventListener listener){
        photoAccessor.getPhoto(key,listener);
    }

    @Override
    public void getLastPhoto(ValueEventListener listener){
        photoAccessor.getLastPhoto(listener);
    }
}
