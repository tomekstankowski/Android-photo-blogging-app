package com.tomaszstankowski.trainingapplication.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Generic class accessing Firebase Database.
 */

public class DataBaseAccessor {
    protected FirebaseDatabase mDatabase;

    public DataBaseAccessor(){
        mDatabase = FirebaseDatabase.getInstance();
    }

    protected Task<Void> setValue(String path, Object item){
        return mDatabase.getReference(path).setValue(item);
    }

    protected void listenToValueChange(String path, ValueEventListener listener){
        mDatabase.getReference(path).addValueEventListener(listener);
    }

    protected void getValue(String path, ValueEventListener listener){
        mDatabase.getReference(path).addListenerForSingleValueEvent(listener);
    }

}
