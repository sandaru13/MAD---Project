package com.example.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.registration.model.User;
import com.example.registration.preference.Consts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText etUsername;
    private EditText etPassword;
    private ProgressBar progress;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        progress = findViewById(R.id.progress);

        progress.setVisibility(View.INVISIBLE);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill the required fields.", Toast.LENGTH_SHORT).show();
                } else {

                    showHideLoading(false);

                    User loginUser = new User();
                    loginUser.setUsername(username);
                    loginUser.setPassword(password);

                    login(loginUser);

                }

            }
        });

    }

    private void login(final User user) {
        mDatabase.orderByChild("username").equalTo(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                showHideLoading(true);

                if (snapshot.exists()) {
                    for (DataSnapshot userSnap : snapshot.getChildren()) {

                        User matchedUser = userSnap.getValue(User.class);

                        assert matchedUser != null;
                        if (matchedUser.getPassword().equals(user.getPassword())) {
                            matchedUser.setId(userSnap.getKey());
                            openUserHandlingActivity(matchedUser);
                            break;
                        }
                    }

                } else {
                    showLoginFailedMessage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showHideLoading(true);
                showLoginFailedMessage();
            }
        });
    }

    private void showLoginFailedMessage() {
        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
    }

    private void openUserHandlingActivity(User loggedUser) {
        Intent intent = new Intent(this, UpdateUserProfileActivity.class);
        intent.putExtra(Consts.USER_EXTRA, loggedUser);
        startActivity(intent);
    }

    private void showHideLoading(boolean shouldHide) {

        btnLogin.setEnabled(shouldHide);

        if (shouldHide) {
            progress.setVisibility(View.INVISIBLE);
            btnLogin.setText("LOGIN");
        } else {
            progress.setVisibility(View.VISIBLE);
            btnLogin.setText("");
        }
    }
}