package com.exercise.ace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity {
    EditText title, description, price, phone;
    private Button button3;
    DatabaseReference reff;
    Ad ad;
    Spinner sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        title = (EditText)findViewById(R.id.editTextTextPersonName2);
        description = (EditText)findViewById(R.id.editTextTextPersonName3);
        sp1 = (Spinner) findViewById(R.id.spinner);
        price = (EditText)findViewById(R.id.editTextNumber);
        phone = (EditText)findViewById(R.id.editTextPhone);
        button3 = (Button) findViewById(R.id.button5);
        ad = new Ad();
        reff = FirebaseDatabase.getInstance().getReference().child("Advertisements");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAd();
            }
        });
    }

    public void viewAd() {
        Long ph = Long.parseLong(phone.getText().toString().trim());
        float pr = Float.parseFloat(price.getText().toString().trim());

        ad.setTitle(title.getText().toString().trim());
        ad.setDescription(description.getText().toString().trim());
        ad.setCategory(sp1.getSelectedItem().toString().trim());
        ad.setPrice(pr);
        ad.setPhone(ph);

        reff.push().setValue(ad);
        Toast.makeText(MainActivity3.this, "Advertisement Successfully Posted", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity4.class);
        startActivity(intent);
    }
}
