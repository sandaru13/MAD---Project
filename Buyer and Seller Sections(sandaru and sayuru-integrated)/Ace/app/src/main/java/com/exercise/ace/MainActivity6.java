package com.exercise.ace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity6 extends AppCompatActivity {

    private TextView tv1,tv2,tv3,tv4;
    private Spinner s1;
    private Button btn;
    Ad ad;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tv1 = (EditText)findViewById(R.id.editName);
        tv2 = (EditText)findViewById(R.id.editDescription);
        s1 = (Spinner)findViewById(R.id.spinner);
        tv3 = (EditText)findViewById(R.id.editPrice);
        tv4 = (EditText)findViewById(R.id.editContact);
        btn = (Button) findViewById(R.id.button5);

        ad = new Ad();
        reff = FirebaseDatabase.getInstance().getReference().child("Ad").child("1");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String title = snapshot.child("title").getValue().toString();
                String description = snapshot.child("description").getValue().toString();
                String category = snapshot.child("category").getValue().toString();
                String price = snapshot.child("price").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();
                tv1.setHint(title);
                tv2.setHint(description);
                s1.setSelection(((ArrayAdapter<String>)s1.getAdapter()).getPosition(category));
                tv3.setHint(price);
                tv4.setHint(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               update();
           }
       });


    }

    private void update() {
        ad.setTitle(tv1.getText().toString().trim());
        ad.setDescription(tv2.getText().toString().trim());
        ad.setCategory(s1.getSelectedItem().toString().trim());
        ad.setPrice(tv3.getText().toString().trim());
        ad.setPhone(tv4.getText().toString().trim());

        HashMap map = new HashMap();
        map.put("title", ad.getTitle());
        map.put("description", ad.getDescription());
        map.put("category", ad.getCategory());
        map.put("price", ad.getPrice());
        map.put("phone", ad.getPhone());


        reff.updateChildren(map);
        Toast.makeText(MainActivity6.this, "Advertisement Successfully Updated", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
