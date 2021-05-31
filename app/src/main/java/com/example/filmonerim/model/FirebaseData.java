package com.example.filmonerim.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseData {

    //FirebaseDatabase
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    FirebaseDatabase dataBase =FirebaseDatabase.getInstance();

    private String element;

    public FirebaseData(String element){
        this.element=element;
    }

    public void readData(FirebaseCallback firebaseCallback){

        List<Banners> banners= new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.child("Films").child("Top").child(element).getChildren()){

                    banners.add(new Banners(
                            Integer.parseInt(ds.getKey().toString()),
                            ds.child("Name").getValue().toString(),
                            ds.child("ImageUrl").getValue().toString(),
                            ds.child("VideoId").getValue().toString()
                    ));

                }
                firebaseCallback.onCallback(banners);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface FirebaseCallback {
        void onCallback(List<Banners> list);
    }
}
