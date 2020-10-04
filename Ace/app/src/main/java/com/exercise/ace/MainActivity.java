package com.exercise.ace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button7);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Seller();
            }
        });
    }



    public void Login() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    public void Seller() {
        Intent intent = new Intent(this, MainActivity7.class);
        startActivity(intent);
    }
}