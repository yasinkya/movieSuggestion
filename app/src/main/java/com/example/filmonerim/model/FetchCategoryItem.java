package com.example.filmonerim.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FetchCategoryItem {
    //FirebaseDatabase
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    FirebaseDatabase dataBase =FirebaseDatabase.getInstance();

    private String element;

    public FetchCategoryItem(String element){
        this.element=element;
    }

    public void readData(CategoryCallback categoryCallback){

        List<CategoryItem> categoryItems= new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.child("Films").child("Categories").child(element).getChildren()){

                    categoryItems.add(new CategoryItem(
                            Integer.parseInt(ds.getKey().toString()),
                            ds.child("Name").getValue().toString(),
                            ds.child("ImageUrl").getValue().toString(),
                            ds.child("VideoId").getValue().toString()
                    ));

                }
                categoryCallback.onCallback(categoryItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface CategoryCallback {
        void onCallback(List<CategoryItem> list);
    }
}
