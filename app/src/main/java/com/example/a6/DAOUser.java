package com.example.a6;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class DAOUser {
    private DatabaseReference databaseReference;

    public DAOUser(){
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());

    }
    public Task<Void> add(User u){
        return databaseReference.push().setValue(u);
    }

    public Task<Void> update(String key, HashMap<String,Object> hashMap){
        return  databaseReference.child(key).updateChildren(hashMap);
    }

    public Query get(){
        return databaseReference.orderByKey();
    }
}

