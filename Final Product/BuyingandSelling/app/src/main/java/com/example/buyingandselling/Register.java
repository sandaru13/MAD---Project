package com.example.buyingandselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buyingandselling.MemberDetails.Members;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    Button button;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText1=(EditText)findViewById(R.id.regname);
        editText2=(EditText)findViewById(R.id.regemail);
        editText3=(EditText)findViewById(R.id.regid);
        editText4=(EditText)findViewById(R.id.regphone);
        editText5=(EditText)findViewById(R.id.regage);
        editText6=(EditText)findViewById(R.id.reggender);
        editText7=(EditText)findViewById(R.id.regpass);
        button=(Button)findViewById(R.id.regregister);

        firebaseAuth=FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Member");

                final String namee = editText1.getText().toString();
                final String emaill = editText2.getText().toString();
                final String idd = editText3.getText().toString();
                final String phonee = editText4.getText().toString();
                final String agee = editText5.getText().toString();
                final String genderr = editText6.getText().toString();
                final String passwordd = editText7.getText().toString();

                if(namee.equals("")) {
                    editText1.setError("Name is required");
                }if(idd.equals("")){
                    editText3.setError("ID is required");
                }if(emaill.equals("")){
                    editText2.setError("Email is required");
                }if(phonee.equals("") || phonee.length() < 10){
                    editText4.setError("Phone number is required");
                }if(agee.equals("")){
                    editText5.setError("Age is required");
                }if(genderr.equals("")){
                    editText6.setError("Gender is required");
                }if(passwordd.equals("")){
                    editText7.setError("Password is required");
                }
                else{

                    firebaseAuth.createUserWithEmailAndPassword(emaill, passwordd)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String userid = user.getUid();

                                        Members members = new Members(namee,emaill,userid,phonee,agee,genderr,passwordd);
                                        reference.child(userid).setValue(members);

                                        System.out.println(members);
                                        Toast.makeText(Register.this, "Registration Complete!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register.this, AdvertiesmentList.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Register.this, "Registration Failed!", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                }
            }
        });
    }
}