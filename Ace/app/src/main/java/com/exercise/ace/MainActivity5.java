package com.exercise.ace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity5 extends AppCompatActivity {
    private Button btn, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        btn = (Button) findViewById(R.id.button6);
        btn.setOnClickListener(new View.OnClickListener() {
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
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void Seller() {
        Intent intent = new Intent(this, MainActivity7.class);
        startActivity(intent);
    }
}
