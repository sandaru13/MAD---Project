package com.example.adzz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button buttonAdd;
    EditText editTextName;
    Spinner spinner1;
    DatabaseReference reff;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editText1);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        buttonAdd  =(Button) findViewById(R.id.btn1);
        product = new Product();
        reff = FirebaseDatabase.getInstance().getReference().child("Product");
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDetails();
            }
        });

    }

    private void addDetails(){

        /*String name = editTextName.getText().toString().trim();
        String cat = spinner1.getSelectedItem().toString();*/

        product.setName(editTextName.getText().toString().trim());
        product.setCato(spinner1.getSelectedItem().toString().trim());

        reff.push().setValue(product);
        Toast.makeText(this, "Product Added", Toast.LENGTH_LONG).show();

        //previous code

        /*if(!TextUtils.isEmpty(name)){

            String id = databaseProd.push().getKey();
            Product prod = new Product(id, name, cat);
            databaseProd.child(id).setValue(prod);
            Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "you must Enter a Product Name", Toast.LENGTH_SHORT).show();
        }*/
    }
}
