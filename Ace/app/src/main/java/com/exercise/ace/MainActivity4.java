package com.exercise.ace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity4 extends AppCompatActivity {
    private Button button4;
    private Button button5;
    private Button button6;
    private Button btn;
    TextView a, b, d, e;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        a = (TextView)findViewById(R.id.title);
        b = (TextView)findViewById(R.id.description);
        d = (TextView)findViewById(R.id.price);
        e = (TextView)findViewById(R.id.phone);
        btn = (Button)findViewById(R.id.button12);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff = FirebaseDatabase.getInstance().getReference().child("Ad").child("-MIe472a7zEkFWEZvOQ-");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String title = snapshot.child("title").getValue().toString();
                        String description = snapshot.child("description").getValue().toString();
                        String price = snapshot.child("price").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        a.setText(title);
                        b.setText(description);
                        d.setText(price);
                        e.setText(phone);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity4.this, "Advertisement Not Loaded Properly", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        button4 = (Button) findViewById(R.id.button9);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Home();
            }
        });

        button5 = (Button) findViewById(R.id.button10);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit();
            }
        });

        button6 = (Button) findViewById(R.id.button11);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete();
            }
        });
    }

    public void Home() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Edit() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    public void Delete() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
}