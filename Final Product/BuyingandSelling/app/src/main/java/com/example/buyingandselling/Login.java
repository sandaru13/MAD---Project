package com.example.buyingandselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    TextView textView1;
    TextView textView2;
    Button button;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText1=(EditText)findViewById(R.id.email);
        editText2=(EditText)findViewById(R.id.password);
        textView1=(TextView)findViewById(R.id.register);
        textView2=(TextView)findViewById(R.id.forgetpass);
        button=(Button)findViewById(R.id.login);

        firebaseAuth=FirebaseAuth.getInstance();

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emaill = editText1.getText().toString();
                final String passwordd = editText2.getText().toString();

                if(emaill.equals("")) {
                    editText1.setError("Email is required");
                }if(passwordd.equals("")){
                    editText2.setError("Password is required");
                }

                firebaseAuth.signInWithEmailAndPassword(emaill, passwordd)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, AdvertiesmentList.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });

    }
}