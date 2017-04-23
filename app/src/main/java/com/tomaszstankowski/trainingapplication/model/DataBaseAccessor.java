package com.tomaszstankowski.trainingapplication.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Generic class accessing Firebase Database.
 */

public class DataBaseAccessor {

    protected Task<Void> setValue(String path, Object item){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(path).setValue(item);
    }

    protected void getValue(String path, ValueEventListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(path).addValueEventListener(listener);
    }

}
