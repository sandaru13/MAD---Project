package com.example.mad_adzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;



import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    //n
    private EditText search;
    ArrayList<Products> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleviewProducts);

        //n
        search = (EditText) findViewById(R.id.edit);
        arrayList = new ArrayList<>();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    searchProduct(editable.toString());
                }
                else{
                    searchProduct("");
                }
            }
        });


        new FirebaseDatabaseHelp().readProducts(new FirebaseDatabaseHelp.DataStatus() {

            @Override
            public void DataIsLoaded(List<Products> product, List<String> keys) {
                new RecyclerViewConfig().setConfig(mRecyclerView, MainActivity.this, product,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });


    }


    //n
    private void searchProduct(String editable) {
        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("title").startAt(editable).endAt(editable + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot dss: dataSnapshot.getChildren()){
                    final Products prod = dss.getValue(Products.class);
                    arrayList.add(prod);
                }
                MyAdapter myAdapter = new MyAdapter(getApplicationContext(), arrayList);
                mRecyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}