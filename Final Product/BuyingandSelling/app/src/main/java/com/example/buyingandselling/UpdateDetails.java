package com.example.buyingandselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.buyingandselling.Model.AddDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UpdateDetails extends AppCompatActivity {

    ImageView imageView;
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    EditText e5;
    Button button;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        imageView=(ImageView)findViewById(R.id.imageViewu);
        e1=(EditText)findViewById(R.id.nameu);
        e2=(EditText)findViewById(R.id.contactu);
        e3=(EditText)findViewById(R.id.addressu);
        e4=(EditText)findViewById(R.id.priceu);
        e5=(EditText)findViewById(R.id.discriptionu);
        button=(Button)findViewById(R.id.update);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        myRef = FirebaseDatabase.getInstance().getReference().child("Buying&Selling").child(userid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("name").getValue().toString();
                String contact = snapshot.child("contact").getValue().toString();
                String address = snapshot.child("address").getValue().toString();
                String price = snapshot.child("price").getValue().toString();
                String discription = snapshot.child("discription").getValue().toString();
                String image = snapshot.child("imageId").getValue().toString();

                e1.setText(name);
                e2.setText(contact);
                e3.setText(address);
                e4.setText(price);
                e5.setText(discription);
                Picasso.get().load(image).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = e1.getText().toString();
                String contact = e2.getText().toString();
                String address = e3.getText().toString();
                String price = e4.getText().toString();
                String discription = e5.getText().toString();



                HashMap map = new HashMap();
                map.put("name", name);
                map.put("contact",contact);
                map.put("address",address);
                map.put("price",price);
                map.put("discription",discription);

                myRef.updateChildren(map);

                Toast.makeText(UpdateDetails.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateDetails.this, AdvertiesmentList.class);
                startActivity(intent);

            }
        });

    }

}