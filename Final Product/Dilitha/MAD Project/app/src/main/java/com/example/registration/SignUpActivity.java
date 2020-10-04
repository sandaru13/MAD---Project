package com.example.registration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.registration.model.User;
import com.example.registration.preference.Consts;
import com.example.registration.preference.StoreDataManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button SignUp;
    private User usr;
    private EditText etUsername, etPassword, etConfirmPassword, etName, etEmail, etPhoneNumber;
    private ProgressBar progress;
    private String selectedCity = "";
    private Button btnSignUp;
    private DatabaseReference mDatabase;
    private StoreDataManager preferenceManager;
    private long userID = 0;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_password_confirm);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        progress = findViewById(R.id.progress);

        progress.setVisibility(View.INVISIBLE);
        builder = new AlertDialog.Builder(this);

        preferenceManager = new StoreDataManager(this);

        // Initializing Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userID = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSignUp = findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });

        Spinner spinner = findViewById(R.id.sp_city);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.City, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter((adapter));
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        selectedCity = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void openActivity() {

        preferenceManager.putPreference(Consts.IS_REGISTERED, "1");

        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    private void validateFields() {

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        final String confirmPassword = etConfirmPassword.getText().toString();
        final String name = etName.getText().toString();
        final String email = etEmail.getText().toString();
        final String phoneNumber = etPhoneNumber.getText().toString();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || selectedCity.isEmpty()) {
            Toast.makeText(this, "Please fill the required fields.", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password and Confirm Password must be same.", Toast.LENGTH_SHORT).show();
        } else {

            showHideLoading(false);

            User createUser = new User();
            createUser.setUsername(username);
            createUser.setEmail(email);
            createUser.setPassword(password);
            createUser.setName(name);
            createUser.setPhoneNumber(phoneNumber);
            createUser.setCity(selectedCity);

            registerUserOnFirebase(createUser);

        }
    }

    private void registerUserOnFirebase(User user) {
        mDatabase.child(String.valueOf(userID + 1)).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showHideLoading(true);
                showSuccessMessage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showHideLoading(true);
                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSuccessMessage() {

        builder
                .setMessage("User successfully created")
                .setTitle("Success")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        openActivity();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showHideLoading(boolean shouldHide) {

        btnSignUp.setEnabled(shouldHide);

        if (shouldHide) {
            progress.setVisibility(View.INVISIBLE);
            btnSignUp.setText("SIGN UP");
        } else {
            progress.setVisibility(View.VISIBLE);
            btnSignUp.setText("");
        }
    }

}