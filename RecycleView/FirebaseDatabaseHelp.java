package com.example.mad_adzz;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelp {
    private FirebaseDatabase sDb;
    private DatabaseReference sRef;
    private List<Products> product = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Products> product, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelp() {
        sDb = FirebaseDatabase.getInstance();
        sRef = sDb.getReference("Products");
    }

    public void readProducts(final DataStatus dataStatus){

        sRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                product.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Products pro = keyNode.getValue(Products.class);
                    product.add(pro);
                }
                dataStatus.DataIsLoaded(product,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}


