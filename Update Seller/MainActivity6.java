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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);

        tv1 = (EditText)findViewById(R.id.editName);
        tv2 = (EditText)findViewById(R.id.editDescription);
        s1 = (Spinner)findViewById(R.id.spinner);
        tv3 = (EditText)findViewById(R.id.editPrice);
        tv4 = (EditText)findViewById(R.id.editContact);
        btn = (Button) findViewById(R.id.button5);

        ad = new Ad();
        reff = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("-MIhGSoyAzpD922m1W66");
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
                s1.setSelection(((ArrayAdapter<String>)s1.getAdapter()).getPosition("s1"));
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
        Long ph = Long.parseLong(tv4.getText().toString().trim());
        float pr = Float.parseFloat(tv3.getText().toString().trim());

        ad.setTitle(tv1.getText().toString().trim());
        ad.setDescription(tv2.getText().toString().trim());
        ad.setCategory(s1.getSelectedItem().toString().trim());
        ad.setPrice(pr);
        ad.setPhone(ph);

        reff.push().setValue(ad);
        Toast.makeText(MainActivity6.this, "Advertisement Successfully Updated", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
