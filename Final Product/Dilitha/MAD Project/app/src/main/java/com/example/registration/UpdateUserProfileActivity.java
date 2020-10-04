package com.example.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.registration.model.User;
import com.example.registration.preference.Consts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateUserProfileActivity extends AppCompatActivity {

    ProgressDialog pd;
    private User user;
    private static int UPDATE_USER_REQUEST = 1000;
    private TextView tvUsername, tvName, tvCity, tvPhoneNumber, tvEmail;
    private DatabaseReference mDatabase;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuserprofile);

        if (getIntent().hasExtra(Consts.USER_EXTRA)) {
            user = (User) getIntent().getSerializableExtra(Consts.USER_EXTRA);
        } else {
            finish();
        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        builder = new AlertDialog.Builder(this);

        pd = new ProgressDialog(this);
        pd.setMessage("Deleting User");

        tvUsername = findViewById(R.id.tv_username);
        tvName = findViewById(R.id.tv_name);
        tvCity = findViewById(R.id.tv_city);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        tvEmail = findViewById(R.id.tv_email);
        Button btnUpdate = findViewById(R.id.btn_edit);
        Button btnDelete = findViewById(R.id.btn_delete);

        loadUserData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUserProfileActivity.this, UserProfileActivity.class);
                intent.putExtra(Consts.USER_EXTRA, user);
                startActivityForResult(intent, UPDATE_USER_REQUEST);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

    }

    private void deleteUser() {
        builder
                .setMessage("Are you sure you want to delete the user ?")
                .setTitle("Delete User")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        proceedToDelete();
                    }
                }).
                setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void proceedToDelete() {
        pd.show();
        mDatabase.orderByChild("username").equalTo(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.hide();
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                    if (userSnap.getKey().equals(user.getId())) {
                        userSnap.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(UpdateUserProfileActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.hide();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_USER_REQUEST && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra(Consts.USER_EXTRA)) {
                user = (User) data.getSerializableExtra(Consts.USER_EXTRA);
                loadUserData();
            }
        }
    }

    private void loadUserData() {
        tvUsername.setText(user.getUsername());
        tvName.setText(user.getName());
        tvCity.setText(user.getCity());
        tvPhoneNumber.setText(user.getPhoneNumber());
        tvEmail.setText(user.getEmail());
    }
}