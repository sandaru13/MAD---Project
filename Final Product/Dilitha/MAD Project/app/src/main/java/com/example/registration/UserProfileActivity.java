package com.example.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.registration.model.User;
import com.example.registration.preference.Consts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    ProgressDialog pd;
    private User user;
    private EditText etUsername, etPassword, etName, etEmail, etPhoneNumber;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        // Initializing Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        pd = new ProgressDialog(this);
        pd.setMessage("Updating User");

        ImageButton btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phone_number);

        if (getIntent().hasExtra(Consts.USER_EXTRA)) {
            user = (User) getIntent().getSerializableExtra(Consts.USER_EXTRA);
            loadUserDetails();
        } else {
            finish();
        }

    }

    private void validateFields() {

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        final String name = etName.getText().toString();
        final String email = etEmail.getText().toString();
        final String phoneNumber = etPhoneNumber.getText().toString();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please fill the required fields.", Toast.LENGTH_SHORT).show();
        } else {

            pd.show();

            User updateUser = new User();
            updateUser.setUsername(username);
            updateUser.setEmail(email);
            updateUser.setPassword(password);
            updateUser.setName(name);
            updateUser.setPhoneNumber(phoneNumber);
            updateUser.setCity(user.getCity());

            updateUser(updateUser);

        }
    }

    private void updateUser(final User updateUser) {
        mDatabase.orderByChild("username").equalTo(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.hide();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    final String currentUserKey = child.getKey();
                    assert currentUserKey != null;
                    dataSnapshot.getRef().child(currentUserKey).setValue(updateUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                updateUser.setId(currentUserKey);
                                Intent extraIntent = new Intent();
                                extraIntent.putExtra(Consts.USER_EXTRA, updateUser);
                                setResult(RESULT_OK, extraIntent);
                                finish();
                            }
                        }
                    });
                    break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.hide();
            }
        });
    }

    private void loadUserDetails() {

        etUsername.setText(user.getUsername());
        etPassword.setText(user.getPassword());
        etName.setText(user.getName());
        etEmail.setText(user.getEmail());
        etPhoneNumber.setText(user.getPhoneNumber());

    }

}