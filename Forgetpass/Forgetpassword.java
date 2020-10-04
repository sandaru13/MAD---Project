package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpassword extends AppCompatActivity {

    private Button resetPass;
    private EditText et;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        et = (EditText) findViewById(R.id.edit);
        resetPass = (Button) findViewById(R.id.passwordreset);
        mAuth = FirebaseAuth.getInstance();

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = et.getText().toString();
                if (TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(Forgetpassword.this, "Please Enter your Valid Email", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Forgetpassword.this, "Please Check your Mail", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Forgetpassword.this, LoginActivity.class));
                            } else {
                                String errorMsg = task.getException().getMessage();
                                Toast.makeText(Forgetpassword.this, "Error Occured -> " + errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }

        });
    }
}