package com.exercise.ace;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelp {
    public FirebaseDatabase sDb;
    public DatabaseReference sRef;
    public List<Ad> ad = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Ad> ad, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelp() {
        sDb = FirebaseDatabase.getInstance();
        sRef = sDb.getReference("Ad");
    }

    public void readProducts(final DataStatus dataStatus){

        sRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ad.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Ad ado= keyNode.getValue(Ad.class);
                    ad.add(ado);
                }
                dataStatus.DataIsLoaded(ad,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
